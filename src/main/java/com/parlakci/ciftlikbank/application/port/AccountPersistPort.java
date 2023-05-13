package com.parlakci.ciftlikbank.application.port;

import com.parlakci.ciftlikbank.domain.model.Account;

public interface AccountPersistPort {
    Account retrieveAccountByUid(String uid);
}
