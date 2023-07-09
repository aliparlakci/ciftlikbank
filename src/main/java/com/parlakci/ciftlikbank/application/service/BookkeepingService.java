package com.parlakci.ciftlikbank.application.service;

import com.parlakci.ciftlikbank.application.port.AccountPersistPort;
import com.parlakci.ciftlikbank.application.port.AccountSnapshotPersistPort;
import com.parlakci.ciftlikbank.application.port.ExchangePersistPort;
import com.parlakci.ciftlikbank.application.port.TransactionPersistPort;
import com.parlakci.ciftlikbank.domain.exception.BusinessException;
import com.parlakci.ciftlikbank.domain.model.Account;
import com.parlakci.ciftlikbank.domain.model.AccountSnapshot;
import com.parlakci.ciftlikbank.domain.model.Transaction;
import com.parlakci.ciftlikbank.domain.model.vo.ExchangeVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookkeepingService {

    private final TransactionPersistPort transactionPersistPort;
    private final AccountSnapshotPersistPort accountSnapshotPersistPort;
    private final AccountPersistPort accountPersistPort;
    private final ExchangePersistPort exchangePersistPort;

    public BigDecimal retrieveAccountBalance(String accountUid) {
        Account account = accountPersistPort.retrieveAccountByUid(accountUid);
        Optional<AccountSnapshot> optionalLatestAccountSnapshot = accountSnapshotPersistPort.retrieveLatestAccountSnapshot(account.uid());
        Transaction latestAccountTransaction = transactionPersistPort.retrieveLatestAccountTransaction(account.uid());

        return optionalLatestAccountSnapshot
                .map(latestAccountSnapshot -> latestAccountTransaction.createdAt().isBefore(latestAccountSnapshot.createdAt())
                        ? latestAccountSnapshot.balance()
                        : this.takeAccountSnapshot(latestAccountSnapshot).balance())
                .orElseGet(() -> this.takeAccountSnapshot(account.uid()).balance());
    }

    public void deposit(String accountUid, BigDecimal amount) {
        ensureNonnegative(amount);
        transactionPersistPort.create(accountUid, amount);
    }

    public void withdraw(String accountUid, BigDecimal amount) {
        ensureNonnegative(amount);
        BigDecimal accountBalance = this.retrieveAccountBalance(accountUid);
        if (accountBalance.compareTo(amount) < 0) {
            throw new BusinessException("Insufficient funds!");
        }
        transactionPersistPort.create(accountUid, amount.negate());
    }

    @Transactional
    public void exchange(ExchangeVo exchangeVo, BigDecimal rate, BigDecimal depositAmount, BigDecimal withdrawAmount) {
        BigDecimal withdrawAccountBalance = this.retrieveAccountBalance(exchangeVo.getWithdrawAccountUid());
        ensureNonnegative(withdrawAccountBalance.subtract(withdrawAmount));
        transactionPersistPort.create(exchangeVo.getWithdrawAccountUid(), withdrawAmount.negate(), exchangeVo.getUid());
        transactionPersistPort.create(exchangeVo.getDepositAccountUid(), depositAmount, exchangeVo.getUid());
        exchangePersistPort.create(exchangeVo, rate);
    }

    private AccountSnapshot takeAccountSnapshot(AccountSnapshot lastSnapshot) {
        String accountUid = lastSnapshot.account().uid();
        List<Transaction> transactions = transactionPersistPort.retrieveTransactions(accountUid, lastSnapshot.createdAt());
        BigDecimal net = lastSnapshot.balance().add(calculateNetBalance(transactions));
        return accountSnapshotPersistPort.newSnapshot(accountUid, net);
    }

    private AccountSnapshot takeAccountSnapshot(String accountUid) {
        List<Transaction> transactions = transactionPersistPort.retrieveTransactions(accountUid);
        BigDecimal net = calculateNetBalance(transactions);
        return accountSnapshotPersistPort.newSnapshot(accountUid, net);
    }

    private static BigDecimal calculateNetBalance(List<Transaction> transactions) {
        return transactions.stream()
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, (subtotal, transaction) -> transaction.add(subtotal));
    }

    private static void ensureNonnegative(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Withdraw amount cannot be negative!");
        }
    }
}
