package com.parlakci.ciftlikbank.application.port;

import com.parlakci.ciftlikbank.domain.model.Account;
import com.parlakci.ciftlikbank.domain.model.Currency;

public interface AccountPersistPort {

    Account retrieveAccountByUid(String uid);

    Account createAccount(String uid, String ownerEmail, Currency currency);

}
