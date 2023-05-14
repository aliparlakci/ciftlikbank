package com.parlakci.ciftlikbank.application.service;

import com.parlakci.ciftlikbank.adapter.rest.request.AccountRequest;
import com.parlakci.ciftlikbank.adapter.rest.response.AccountResponse;
import com.parlakci.ciftlikbank.application.port.AccountPersistPort;
import com.parlakci.ciftlikbank.domain.model.Account;
import com.parlakci.ciftlikbank.domain.model.Currency;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final BookkeepingService bookkeepingService;
    private final AccountPersistPort accountPersistPort;

    public AccountResponse retrieveAccount(String accountUid) {
        BigDecimal accountBalance = bookkeepingService.retrieveAccountBalance(accountUid);
        Account account = accountPersistPort.retrieveAccountByUid(accountUid);
        return AccountResponse.from(account, accountBalance);
    }

    public AccountResponse createAccount(AccountRequest accountRequest) {
        String uid = UUID.randomUUID().toString();
        String email = accountRequest.getEmail();
        Currency currency = Currency.fromValue(accountRequest.getCurrency());

        Account account = accountPersistPort.createAccount(uid, email, currency);
        return AccountResponse.from(account, BigDecimal.ZERO);
    }
}
