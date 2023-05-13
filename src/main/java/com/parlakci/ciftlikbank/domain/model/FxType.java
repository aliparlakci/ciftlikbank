package com.parlakci.ciftlikbank.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FxType {
    USD_TRY("USD_TRY"), TRY_USD("TRY_USD");

    private final String value;
}
