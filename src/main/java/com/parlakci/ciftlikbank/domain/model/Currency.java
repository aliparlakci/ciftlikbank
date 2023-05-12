package com.parlakci.ciftlikbank.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Currency {
    USD("USD"), TRY("TRY");

    private final String value;
}
