package com.parlakci.ciftlikbank.domain.model;

import java.io.Serializable;
import java.time.ZonedDateTime;

public record Transfer(Long id,
                       String uid,
                       Account account,
                       TransferType transferType,
                       ZonedDateTime createdAt,
                       ZonedDateTime updatedAt) implements Serializable {
}
