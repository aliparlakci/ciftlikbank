package com.parlakci.ciftlikbank.application.facade;

import com.parlakci.ciftlikbank.adapter.rest.response.AccountResponse;
import com.parlakci.ciftlikbank.application.port.AccountPersistPort;
import com.parlakci.ciftlikbank.domain.model.Account;
import com.parlakci.ciftlikbank.application.service.BalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountRetrieveFacade {
    private final BalanceService balanceService;
    private final AccountPersistPort accountPersistPort;

    public AccountResponse retrieveAccount(String accountUid) {
        BigDecimal accountBalance = balanceService.retrieveAccountBalance(accountUid);
        Account account = accountPersistPort.retrieveAccountByUid(accountUid);
        return AccountResponse.from(account, accountBalance);
    }
}
