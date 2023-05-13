package com.parlakci.ciftlikbank.adapter.jpa.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacationRepository extends JpaRepository<TransactionEntity, Long> {
}
