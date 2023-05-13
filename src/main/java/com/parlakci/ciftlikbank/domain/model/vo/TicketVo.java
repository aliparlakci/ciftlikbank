package com.parlakci.ciftlikbank.domain.model.vo;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

public record TicketVo(
        String ticket,
        ZonedDateTime expiresAt) {
}
