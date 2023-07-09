package com.parlakci.ciftlikbank.adapter.jpa.accountSnapshot;

import com.parlakci.ciftlikbank.adapter.jpa.account.AccountEntity;
import com.parlakci.ciftlikbank.adapter.jpa.account.AccountRepository;
import com.parlakci.ciftlikbank.application.port.AccountSnapshotPersistPort;
import com.parlakci.ciftlikbank.domain.model.AccountSnapshot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountSnapshotPersistJpaAdapter implements AccountSnapshotPersistPort {

    private final AccountSnapshotRepository accountSnapshotRepository;
    private final AccountRepository accountRepository;

    @Override
    public Optional<AccountSnapshot> retrieveLatestAccountSnapshot(String accountUid) {
        return accountSnapshotRepository.findFirstByAccount_UidOrderByIdDesc(accountUid)
                .map(AccountSnapshotEntity::toModel);
    }

    @Override
    public AccountSnapshot newSnapshot(String accountUid, BigDecimal net) {
        AccountSnapshotEntity accountSnapshotEntity = new AccountSnapshotEntity();
        AccountEntity account = accountRepository.findByUid(accountUid);

        accountSnapshotEntity.setAccount(account);
        accountSnapshotEntity.setBalance(net);
        accountSnapshotEntity.setCreatedAt(ZonedDateTime.now());

        return accountSnapshotRepository.saveAndFlush(accountSnapshotEntity).toModel();
    }

}
