package com.parlakci.ciftlikbank.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Currency {
    USD(0), TRY(1);

    private final int value;

    public static Currency fromValue(int value) {
        return Arrays.stream(values())
                .filter(currency -> currency.getValue() == value)
                .findFirst()
                .orElseThrow();
    }
}
