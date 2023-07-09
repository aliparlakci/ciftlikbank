package com.parlakci.ciftlikbank.application.port;

import com.parlakci.ciftlikbank.domain.model.AccountSnapshot;

import java.math.BigDecimal;
import java.util.Optional;

public interface AccountSnapshotPersistPort {

    Optional<AccountSnapshot> retrieveLatestAccountSnapshot(String accountUid);

    AccountSnapshot newSnapshot(String accountUid, BigDecimal net);

}
