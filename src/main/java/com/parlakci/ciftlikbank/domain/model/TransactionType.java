package com.parlakci.ciftlikbank.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionType {
    FX("Fx"),
    DEPOSIT("DEPOSIT"),
    WITHDRAW("WITHDRAW");

    private final String value;
}
