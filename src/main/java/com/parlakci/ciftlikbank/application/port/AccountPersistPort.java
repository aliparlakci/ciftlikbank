package com.parlakci.ciftlikbank.application.port;

import com.parlakci.ciftlikbank.domain.model.Account;
import com.parlakci.ciftlikbank.domain.model.Currency;

import java.util.List;

public interface AccountPersistPort {

    List<Account> retrieveAccountsByCurrency(Currency currency);

    Account retrieveAccountByUid(String uid);

    Account createAccount(String uid, String ownerEmail, Currency currency);

}
