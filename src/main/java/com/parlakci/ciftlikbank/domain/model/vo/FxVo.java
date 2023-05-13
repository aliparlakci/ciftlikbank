package com.parlakci.ciftlikbank.domain.model.vo;

import com.parlakci.ciftlikbank.adapter.rest.request.FxRequest;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class FxVo {
    String depositAccountUid;
    String withdrawAccountUid;
    String ticket;
    BigDecimal amount;

    public static FxVo from(FxRequest fxRequest) {
        FxVo fxVo = new FxVo();
        fxVo.amount = fxRequest.getAmount();
        fxVo.depositAccountUid = fxRequest.getDepositAccountUid();
        fxVo.withdrawAccountUid = fxRequest.getWithdrawAccountUid();
        fxVo.ticket = fxRequest.getTicket();
        return fxVo;
    }
}
