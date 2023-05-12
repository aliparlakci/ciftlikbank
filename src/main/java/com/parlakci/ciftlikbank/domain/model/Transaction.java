package com.parlakci.ciftlikbank.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record Transaction(Long id,
                          BigDecimal amount,
                          Account account,
                          TransactionType transactionType,
                          String referenceUid,
                          ZonedDateTime createdAt,
                          ZonedDateTime updatedAt) implements Serializable {
}
