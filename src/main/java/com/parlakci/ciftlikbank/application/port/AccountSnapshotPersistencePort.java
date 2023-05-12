package com.parlakci.ciftlikbank.application.port;

import com.parlakci.ciftlikbank.domain.model.AccountSnapshot;

import java.math.BigDecimal;

public interface AccountSnapshotPersistencePort {
    AccountSnapshot retrieveLatest(String accountUid);

    AccountSnapshot newSnapshot(String accountUid, BigDecimal net);
}
