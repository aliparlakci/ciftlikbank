package com.parlakci.ciftlikbank.adapter.jpa.account;

import com.parlakci.ciftlikbank.domain.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    AccountEntity findByUid(String uid);

    List<AccountEntity> findAllByCurrency(Currency currency);
}
