package com.parlakci.ciftlikbank.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum TransactionType {
    EXCHANGE("EXCHANGE"),
    DEPOSIT("DEPOSIT"),
    WITHDRAW("WITHDRAW");

    private final String value;
}
