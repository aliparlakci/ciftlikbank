package com.parlakci.ciftlikbank.adapter.jpa.accountSnapshot;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountSnapshotRepository extends JpaRepository<AccountSnapshotEntity, Long> {

    Optional<AccountSnapshotEntity> findFirstByAccount_UidOrderByIdDesc(String accountUid);

}
