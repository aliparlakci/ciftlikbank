package com.parlakci.ciftlikbank.domain.service;

import com.parlakci.ciftlikbank.application.port.AccountSnapshotPersistPort;
import com.parlakci.ciftlikbank.application.port.TransactionPersistPort;
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
public class BalanceService {

    private final TransactionPersistPort transactionPersistPort;
    private final AccountSnapshotPersistPort accountSnapshotPersistPort;

    @Transactional
    public BigDecimal retrieveAccountBalance(String accountUid) {
        // TODO caching
        AccountSnapshot accountSnapshot = accountSnapshotPersistPort.retrieveLatest(accountUid);
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
        AccountSnapshot accountSnapshot = accountSnapshotPersistPort.retrieveLatest(accountUid);
        BigDecimal net = accountSnapshot.balance().add(calculateNetAsset(transactions));
        return accountSnapshotPersistPort.newSnapshot(accountUid, net);
    }

    private static BigDecimal calculateNetAsset(List<Transaction> transactions) {
        return transactions.stream()
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, (subtotal, transaction) -> transaction.add(subtotal));
    }
}
