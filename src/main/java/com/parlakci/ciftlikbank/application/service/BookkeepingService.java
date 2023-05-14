package com.parlakci.ciftlikbank.application.service;

import com.parlakci.ciftlikbank.application.port.AccountPersistPort;
import com.parlakci.ciftlikbank.application.port.AccountSnapshotPersistPort;
import com.parlakci.ciftlikbank.application.port.TransactionPersistPort;
import com.parlakci.ciftlikbank.domain.exception.BusinessException;
import com.parlakci.ciftlikbank.domain.model.Account;
import com.parlakci.ciftlikbank.domain.model.AccountSnapshot;
import com.parlakci.ciftlikbank.domain.model.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookkeepingService {

    private final TransactionPersistPort transactionPersistPort;
    private final AccountSnapshotPersistPort accountSnapshotPersistPort;
    private final AccountPersistPort accountPersistPort;

    public void deposit(String accountUid, BigDecimal amount) {
        this.deposit(accountUid, amount, null);
    }

    public void exchangeDeposit(String accountUid, BigDecimal amount, String referenceUid) {
        this.deposit(accountUid, amount, referenceUid);
    }

    public void withdraw(String accountUid, BigDecimal amount) {
        this.withdraw(accountUid, amount, null);
    }

    public void exchangeWithdraw(String accountUid, BigDecimal amount, String referenceUid) {
        this.withdraw(accountUid, amount, referenceUid);
    }

    private void deposit(String accountUid, BigDecimal amount, String referenceUid) {
        // TODO locks
        // TODO invalidate cache
        ensureNonnegative(amount);
        transactionPersistPort.create(accountUid, amount, referenceUid);
    }

    private void withdraw(String accountUid, BigDecimal amount, String referenceUid) {
        ensureNonnegative(amount);
        // TODO locks
        BigDecimal accountBalance = this.retrieveAccountBalance(accountUid);
        if (amount.compareTo(accountBalance) < 0) {
            throw new BusinessException("Insufficient funds!");
        }
        transactionPersistPort.create(accountUid, amount.negate(), referenceUid);
    }

    private static void ensureNonnegative(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Withdraw amount cannot be negative!");
        }
    }

    @Transactional
    public BigDecimal retrieveAccountBalance(String accountUid) {
        // TODO caching
        Account account = accountPersistPort.retrieveAccountByUid(accountUid);
        AccountSnapshot accountSnapshot = accountSnapshotPersistPort.retrieveLatestAccountSnapshot(accountUid);
        Transaction transaction = transactionPersistPort.retrieveLatestAccountTransaction(accountUid);
        // multiple threads creating redundant account snapshots?
        // we need caching and locking here
        return accountSnapshot.createdAt().isAfter(transaction.createdAt())
                ? this.takeAccountSnapshot(accountUid, accountSnapshot.createdAt()).balance()
                : accountSnapshot.balance();
    }

    @Transactional
    public AccountSnapshot takeAccountSnapshot(String accountUid, ZonedDateTime startDate) {
        // TODO too many db calls because we use uid
        List<Transaction> transactions = transactionPersistPort.retrieveTransactions(accountUid, startDate);
        AccountSnapshot accountSnapshot = accountSnapshotPersistPort.retrieveLatestAccountSnapshot(accountUid);
        BigDecimal net = accountSnapshot.balance().add(calculateNetAsset(transactions));
        return accountSnapshotPersistPort.newSnapshot(accountUid, net);
    }

    private static BigDecimal calculateNetAsset(List<Transaction> transactions) {
        return transactions.stream()
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, (subtotal, transaction) -> transaction.add(subtotal));
    }
}
