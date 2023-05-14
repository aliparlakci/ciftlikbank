package com.parlakci.ciftlikbank.application.port;

import com.parlakci.ciftlikbank.domain.model.Transaction;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public interface TransactionPersistPort {

    Transaction retrieveLatestAccountTransaction(String accountUid);

    List<Transaction> retrieveTransactions(String accountUid, ZonedDateTime startDate);

    void create(String accountUid, BigDecimal amount);

    void create(String accountUid, BigDecimal amount, String referenceUid);
}
