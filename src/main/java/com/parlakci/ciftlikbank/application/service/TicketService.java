package com.parlakci.ciftlikbank.application.service;

import com.parlakci.ciftlikbank.application.port.RateService;
import com.parlakci.ciftlikbank.application.port.TicketPersistPort;
import com.parlakci.ciftlikbank.domain.model.vo.TicketVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketPersistPort ticketPersistPort;
    private final RateService rateService;

    public TicketVo requestTicket() {
        BigDecimal rate = requestNewRate();
        String ticket = UUID.randomUUID().toString();
        ticketPersistPort.saveTicket(ticket, rate);

        return null;
    }

    private BigDecimal requestNewRate() {
        String usdTryRate = rateService.getRates().getConversionRates().getTRY();
        return new BigDecimal(usdTryRate);
    }
}
