package com.parlakci.ciftlikbank.application.service;

import com.parlakci.ciftlikbank.adapter.rest.request.AccountRequest;
import com.parlakci.ciftlikbank.adapter.rest.response.AccountResponse;
import com.parlakci.ciftlikbank.adapter.rest.response.AccountsResponse;
import com.parlakci.ciftlikbank.application.port.AccountPersistPort;
import com.parlakci.ciftlikbank.application.port.TransactionPersistPort;
import com.parlakci.ciftlikbank.domain.model.Account;
import com.parlakci.ciftlikbank.domain.model.Currency;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final BookkeepingService bookkeepingService;
    private final AccountPersistPort accountPersistPort;
    private final TransactionPersistPort transactionPersistPort;

    public AccountResponse retrieveAccount(String accountUid) {
        Account account = accountPersistPort.retrieveAccountByUid(accountUid);
        BigDecimal accountBalance = bookkeepingService.retrieveAccountBalance(accountUid);
        return AccountResponse.from(account, accountBalance);
    }

    public AccountsResponse retrieveAllAccounts(Currency currency) {
        List<AccountResponse> accounts = accountPersistPort.retrieveAccountsByCurrency(currency)
                .stream()
                .map(AccountResponse::from)
                .toList();
        return new AccountsResponse(accounts);
    }

    @Transactional
    public AccountResponse createAccount(AccountRequest accountRequest) {
        String uid = UUID.randomUUID().toString();
        String email = accountRequest.getEmail();
        Currency currency = accountRequest.getCurrency();

        Account account = accountPersistPort.createAccount(uid, email, currency);
        transactionPersistPort.createZeroTransaction(uid);
        return AccountResponse.from(account, BigDecimal.ZERO);
    }
}
