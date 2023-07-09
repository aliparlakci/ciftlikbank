package com.parlakci.ciftlikbank.adapter.redis;

import com.parlakci.ciftlikbank.application.port.TicketPersistPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
public class TicketPersistRedisAdapter implements TicketPersistPort {

    @Override
    public void saveTicket(String ticket, BigDecimal rate) {

    }

    @Override
    public Optional<BigDecimal> retrieveTicket(String ticket) {
        return Optional.empty();
    }
}
