package com.parlakci.ciftlikbank.adapter.jpa.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    AccountEntity findByUid(String uid);

}
