package com.parlakci.ciftlikbank.domain.model;

import java.io.Serializable;
import java.time.ZonedDateTime;

public record Account(Long id,
                      String uid,
                      String ownerEmail,
                      Currency currency,
                      ZonedDateTime createdAt,
                      ZonedDateTime updatedAt) implements Serializable {
}
