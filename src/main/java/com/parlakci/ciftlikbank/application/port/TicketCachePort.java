package com.parlakci.ciftlikbank.application.port;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Optional;

public interface TicketCachePort {

    void saveTicket(String ticket, BigDecimal rate, Duration ttlInSeconds);

    Optional<BigDecimal> retrieveTicket(String ticket);

}
