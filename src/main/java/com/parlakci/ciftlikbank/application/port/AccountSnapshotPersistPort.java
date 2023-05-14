package com.parlakci.ciftlikbank.application.port;

import com.parlakci.ciftlikbank.domain.model.AccountSnapshot;

import java.math.BigDecimal;

public interface AccountSnapshotPersistPort {
    AccountSnapshot retrieveLatestAccountSnapshot(String accountUid);

    AccountSnapshot newSnapshot(String accountUid, BigDecimal net);
}
