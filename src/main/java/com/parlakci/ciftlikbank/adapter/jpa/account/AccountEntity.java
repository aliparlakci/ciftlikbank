package com.parlakci.ciftlikbank.adapter.jpa.account;

import com.parlakci.ciftlikbank.adapter.jpa.EntityBase;
import com.parlakci.ciftlikbank.domain.model.Account;
import com.parlakci.ciftlikbank.domain.model.Currency;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(schema = "account")
public class AccountEntity extends EntityBase {

    @Column(name = "uid", nullable = false)
    private String uid;

    @Column(name = "ownerEmail", nullable = false)
    private String ownerEmail;

    @Column(name = "currency", nullable = false)
    private Currency currency;

    public Account toModel() {
        return new Account(
                this.getId(),
                this.uid,
                this.getOwnerEmail(),
                this.getCurrency(),
                this.getCreatedAt(),
                this.getUpdatedAt()
        );
    }
}
