package com.parlakci.ciftlikbank.domain.model.vo;

import com.parlakci.ciftlikbank.adapter.rest.request.ExchangeRequest;

import java.math.BigDecimal;

public record ExchangeVo(String uid,
                         String depositAccountUid,
                         String withdrawAccountUid,
                         String ticket,
                         BigDecimal amount) {

    public static ExchangeVo from(ExchangeRequest exchangeRequest) {
        return new ExchangeVo(
                null,
                exchangeRequest.getDepositAccountUid(),
                exchangeRequest.getWithdrawAccountUid(),
                exchangeRequest.getTicket(),
                exchangeRequest.getAmount()
        );
    }
}
