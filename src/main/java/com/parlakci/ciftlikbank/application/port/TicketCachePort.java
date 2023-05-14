package com.parlakci.ciftlikbank.application.port;

import java.math.BigDecimal;
import java.util.Optional;

public interface TicketCachePort {

    void saveTicket(String ticket, BigDecimal rate);

    Optional<BigDecimal> retrieveTicket(String ticket);
}
