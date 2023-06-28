package com.parlakci.ciftlikbank.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {
    EXCHANGE("EXCHANGE"),
    DEPOSIT("DEPOSIT"),
    WITHDRAW("WITHDRAW"),
    ZERO("ZERO");

    private final String value;
}
