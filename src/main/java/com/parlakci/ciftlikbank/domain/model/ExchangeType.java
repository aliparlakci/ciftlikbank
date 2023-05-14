package com.parlakci.ciftlikbank.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public enum ExchangeType {
    SELL_USD("SELL_USD"), BUY_USD("BUY_USD");

    private final String value;
}
