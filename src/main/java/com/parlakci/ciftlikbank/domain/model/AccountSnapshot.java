package com.parlakci.ciftlikbank.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record AccountSnapshot(Long id,
                              Account account,
                              BigDecimal balance,
                              ZonedDateTime createdAt,
                              ZonedDateTime updateAt) implements Serializable {
}
