package com.parlakci.ciftlikbank.domain.model.vo;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record TicketVo(
        String ticket,
        BigDecimal rate,
        ZonedDateTime expiresAt) {
}
