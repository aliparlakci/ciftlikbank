package com.parlakci.ciftlikbank.adapter.rest.request;

import com.parlakci.ciftlikbank.domain.model.Currency;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class AccountRequest {

    @NonNull
    String email;

    @NonNull
    Currency currency;
}
