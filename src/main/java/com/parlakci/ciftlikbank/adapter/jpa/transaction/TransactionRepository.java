package com.parlakci.ciftlikbank.adapter.jpa.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    TransactionEntity findFirstByAccount_UidOrderByIdDesc(String accountUid);

    List<TransactionEntity> findAllByAccount_UidAndCreatedAtAfterOrderByIdDesc(String accountUid, ZonedDateTime createdAt);
}
