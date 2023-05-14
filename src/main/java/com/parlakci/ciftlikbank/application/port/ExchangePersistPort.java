package com.parlakci.ciftlikbank.application.port;

import com.parlakci.ciftlikbank.domain.model.Exchange;

public interface ExchangePersistPort {
    void newExchange(Exchange exchange);
}
