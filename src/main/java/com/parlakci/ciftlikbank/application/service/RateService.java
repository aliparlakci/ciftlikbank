package com.parlakci.ciftlikbank.application.service;

import com.parlakci.ciftlikbank.application.port.TicketCachePort;
import com.parlakci.ciftlikbank.domain.model.vo.TicketVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RateService {

    private final TicketCachePort ticketCachePort;

    public TicketVo requestTicket() {
        BigDecimal rate = requestNewRate();
        String ticket = UUID.randomUUID().toString();
        ticketCachePort.saveTicket(ticket, rate);

        return null;
    }

    private static BigDecimal requestNewRate() {
        // TODO implement
        return new BigDecimal(0);
    }
}
