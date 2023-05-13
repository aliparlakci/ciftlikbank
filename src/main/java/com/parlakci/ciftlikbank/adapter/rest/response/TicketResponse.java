package com.parlakci.ciftlikbank.adapter.rest.response;

import com.parlakci.ciftlikbank.domain.model.vo.TicketVo;

import java.time.ZonedDateTime;

public record TicketResponse (
    String ticket,
    ZonedDateTime expiresAt) {

    public static TicketResponse from(TicketVo ticketVo) {
        return new TicketResponse(ticketVo.ticket(), ticketVo.expiresAt());
    }
}
