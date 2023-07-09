package com.parlakci.ciftlikbank.application.service;

import com.parlakci.ciftlikbank.application.port.RatePort;
import com.parlakci.ciftlikbank.application.port.TicketCachePort;
import com.parlakci.ciftlikbank.domain.model.RateResponse;
import com.parlakci.ciftlikbank.domain.model.vo.TicketVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketCachePort ticketCachePort;
    private final RatePort ratePort;

    public TicketVo requestTicket() {
        BigDecimal rate = requestNewRate();
        String ticket = UUID.randomUUID().toString();
        ZonedDateTime expiresAt = ZonedDateTime.now().plusSeconds(30);
        ticketCachePort.saveTicket(ticket, rate, Duration.ofSeconds(30));

        return new TicketVo(ticket, rate, expiresAt);
    }

    private BigDecimal requestNewRate() {
        RateResponse rates = ratePort.getRates();
        return rates.getConversionRates().getTRY();
    }
}
