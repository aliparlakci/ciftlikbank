package com.parlakci.ciftlikbank.adapter.jpa.exchange;

import com.parlakci.ciftlikbank.application.port.ExchangePersistPort;
import com.parlakci.ciftlikbank.domain.model.Exchange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangePersistJpaAdapter implements ExchangePersistPort {
    @Override
    public void newExchange(Exchange exchange) {

    }
}
