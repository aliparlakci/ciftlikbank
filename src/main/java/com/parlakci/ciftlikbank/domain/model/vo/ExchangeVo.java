package com.parlakci.ciftlikbank.domain.model.vo;

import com.parlakci.ciftlikbank.adapter.rest.request.ExchangeRequest;
import com.parlakci.ciftlikbank.domain.model.ExchangeType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ExchangeVo {
    private String uid;
    private String depositAccountUid;
    private String withdrawAccountUid;
    private String ticket;
    private BigDecimal amount;
    private ExchangeType exchangeType;

    public static ExchangeVo from(ExchangeType exchangeType, ExchangeRequest exchangeRequest) {
        return new ExchangeVo(
                null,
                exchangeRequest.getDepositAccountUid(),
                exchangeRequest.getWithdrawAccountUid(),
                exchangeRequest.getTicket(),
                exchangeRequest.getAmount(),
                exchangeType
        );
    }

}
