package com.parlakci.ciftlikbank.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionType {
    FX("FX"),
    TRANSFER("TRANSFER");

    private final String value;
}
