package com.parlakci.ciftlikbank.application.port;

import com.parlakci.ciftlikbank.domain.model.vo.ExchangeVo;

import java.math.BigDecimal;

public interface ExchangePersistPort {

    void create(ExchangeVo exchangeVo, BigDecimal rate);

}
