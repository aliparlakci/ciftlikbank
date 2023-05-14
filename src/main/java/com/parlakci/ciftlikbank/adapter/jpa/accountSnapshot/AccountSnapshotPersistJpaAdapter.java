package com.parlakci.ciftlikbank.adapter.jpa.accountSnapshot;

import com.parlakci.ciftlikbank.application.port.AccountSnapshotPersistPort;
import com.parlakci.ciftlikbank.domain.model.AccountSnapshot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountSnapshotPersistJpaAdapter implements AccountSnapshotPersistPort {
    @Override
    public AccountSnapshot retrieveLatestAccountSnapshot(String accountUid) {
        return null;
    }

    @Override
    public AccountSnapshot newSnapshot(String accountUid, BigDecimal net) {
        return null;
    }
}
