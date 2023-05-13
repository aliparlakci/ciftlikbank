package com.parlakci.ciftlikbank.domain.model.vo;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Builder
@Getter
public record TicketVo(
        String ticket,
        ZonedDateTime expiresAt) {
}
