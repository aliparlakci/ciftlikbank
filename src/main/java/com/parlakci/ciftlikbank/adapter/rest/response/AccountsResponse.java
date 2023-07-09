package com.parlakci.ciftlikbank.adapter.rest.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AccountsResponse {

    List<AccountResponse> accounts;

}
