package com.parlakci.ciftlikbank.adapter.rest.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public abstract class TransferRequestBase {

    @NonNull
    private BigDecimal amount;

}
