package com.parlakci.ciftlikbank.adapter.jpa.transaction;

import com.parlakci.ciftlikbank.adapter.jpa.account.AccountEntity;
import com.parlakci.ciftlikbank.adapter.jpa.account.AccountRepository;
import com.parlakci.ciftlikbank.application.port.TransactionPersistPort;
import com.parlakci.ciftlikbank.domain.model.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionPersistJpaAdapter implements TransactionPersistPort {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public Transaction retrieveLatestAccountTransaction(String accountUid) {
        return transactionRepository.findFirstByAccount_UidOrderByIdDesc(accountUid).toModel();
    }

    public List<Transaction> retrieveTransactions(String accountUid) {
        return transactionRepository.findAllByAccount_UidOrderByIdDesc(accountUid)
                .stream()
                .map(TransactionEntity::toModel)
                .toList();
    }

    public List<Transaction> retrieveTransactions(String accountUid, ZonedDateTime startDate) {
        return transactionRepository.findAllByAccount_UidAndCreatedAtAfterOrderByIdDesc(accountUid, startDate)
                .stream()
                .map(TransactionEntity::toModel)
                .toList();
    }

    public void createZeroTransaction(String accountUid) {
        this.create(accountUid, BigDecimal.ZERO, null);
    }

    public void create(String accountUid, BigDecimal amount) {
        this.create(accountUid, amount, null);
    }

    public void create(String accountUid, BigDecimal amount, String referenceUid) {
        AccountEntity accountEntity = accountRepository.findByUid(accountUid);

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setUid(UUID.randomUUID().toString());
        transactionEntity.setAccount(accountEntity);
        transactionEntity.setAmount(amount);
        transactionEntity.setReferenceUid(referenceUid);
        transactionEntity.setCreatedAt(ZonedDateTime.now());
        transactionRepository.saveAndFlush(transactionEntity);
    }
}
