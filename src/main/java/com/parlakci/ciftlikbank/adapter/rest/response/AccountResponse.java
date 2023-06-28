package com.parlakci.ciftlikbank.adapter.rest.response;

import com.parlakci.ciftlikbank.domain.model.Account;
import com.parlakci.ciftlikbank.domain.model.Currency;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Builder
public class AccountResponse {

    String uid;
    String email;
    Currency currency;
    BigDecimal accountBalance;
    ZonedDateTime createdAt;

    public static AccountResponse from(Account account, BigDecimal balance) {
        return AccountResponse.builder()
                .uid(account.uid())
                .email(account.ownerEmail())
                .currency(account.currency())
                .accountBalance(balance)
                .createdAt(account.createdAt())
                .build();
    }
}
