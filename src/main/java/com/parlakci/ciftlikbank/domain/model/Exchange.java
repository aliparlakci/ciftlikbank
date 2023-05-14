package com.parlakci.ciftlikbank.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record Exchange(Long id,
                       String uid,
                       Account depositAccount,
                       Account withdrawAccount,
                       ExchangeType exchangeType,
                       BigDecimal amount,
                       BigDecimal rate,
                       ZonedDateTime createdAt,
                       ZonedDateTime updatedAt) implements Serializable {
}
