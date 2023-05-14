package com.parlakci.ciftlikbank.adapter.jpa.transaction;

import com.parlakci.ciftlikbank.application.port.TransactionPersistPort;
import com.parlakci.ciftlikbank.domain.model.Transaction;
import com.parlakci.ciftlikbank.domain.model.TransactionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionPersistJpaAdapter implements TransactionPersistPort {

    public void createZeroTransaction(String accountUid) {

    }

    ;

    @Override
    public Transaction retrieveLatestAccountTransaction(String accountUid) {
        return null;
    }

    @Override
    public List<Transaction> retrieveTransactions(String accountUid, ZonedDateTime startDate) {
        return null;
    }

    @Override
    public void create(String accountUid, BigDecimal amount) {

    }

    @Override
    public void create(String accountUid, BigDecimal amount, String referenceUid) {

    }

}
