package com.parlakci.ciftlikbank.adapter.rest.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class AccountRequest {
    @NonNull
    String email;

    @NonNull
    Integer currency;
}
