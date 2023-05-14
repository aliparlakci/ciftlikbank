package com.parlakci.ciftlikbank.application.port;

import com.parlakci.ciftlikbank.domain.model.vo.ExchangeVo;

public interface ExchangePersistPort {
    void create(ExchangeVo exchangeVo);
}
