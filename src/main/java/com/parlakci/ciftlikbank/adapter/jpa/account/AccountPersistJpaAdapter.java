package com.parlakci.ciftlikbank.adapter.jpa.account;

import com.parlakci.ciftlikbank.application.port.AccountPersistPort;
import com.parlakci.ciftlikbank.application.port.TransactionPersistPort;
import com.parlakci.ciftlikbank.domain.model.Account;
import com.parlakci.ciftlikbank.domain.model.Currency;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountPersistJpaAdapter implements AccountPersistPort {

    private final AccountRepository accountRepository;
    private final TransactionPersistPort transactionPersistPort;

    public Account retrieveAccountByUid(String uid) {
        return accountRepository.findByUid(uid).toModel();
    }

    @Transactional
    public Account createAccount(String uid, String ownerEmail, Currency currency) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUid(uid);
        accountEntity.setOwnerEmail(ownerEmail);
        accountEntity.setCurrency(currency);
        accountRepository.save(accountEntity);
        transactionPersistPort.createZeroTransaction(uid);
        return accountEntity.toModel();
    }
}
