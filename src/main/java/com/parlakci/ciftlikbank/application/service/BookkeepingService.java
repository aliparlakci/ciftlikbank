package com.parlakci.ciftlikbank.application.service;

import com.parlakci.ciftlikbank.application.port.AccountPersistPort;
import com.parlakci.ciftlikbank.application.port.AccountSnapshotPersistPort;
import com.parlakci.ciftlikbank.application.port.TransactionPersistPort;
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

    @Transactional
    public BigDecimal retrieveAccountBalance(String accountUid) {
        // TODO caching
        Account account = accountPersistPort.retrieveAccountByUid(accountUid);
        AccountSnapshot accountSnapshot = accountSnapshotPersistPort.retrieveLatestAccountSnapshot(account.uid());
        Transaction transaction = transactionPersistPort.retrieveLatestAccountTransaction(account.uid());

        return accountSnapshot.createdAt().isAfter(transaction.createdAt())
                ? this.takeAccountSnapshot(accountUid, accountSnapshot.createdAt()).balance()
                : accountSnapshot.balance();
    }

    private AccountSnapshot takeAccountSnapshot(String accountUid, ZonedDateTime startDate) {
        List<Transaction> transactions = transactionPersistPort.retrieveTransactions(accountUid, startDate);
        AccountSnapshot accountSnapshot = accountSnapshotPersistPort.retrieveLatestAccountSnapshot(accountUid);
        BigDecimal net = accountSnapshot.balance().add(calculateNetAsset(transactions));
        return accountSnapshotPersistPort.newSnapshot(accountUid, net);
    }

    private static BigDecimal calculateNetAsset(List<Transaction> transactions) {
        return transactions.stream()
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO,
                        (subtotal, transaction) -> transaction.add(subtotal));
    }
}
