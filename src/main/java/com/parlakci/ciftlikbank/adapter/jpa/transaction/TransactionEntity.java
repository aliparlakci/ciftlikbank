package com.parlakci.ciftlikbank.adapter.jpa.transaction;

import com.parlakci.ciftlikbank.adapter.jpa.EntityBase;
import com.parlakci.ciftlikbank.adapter.jpa.account.AccountEntity;
import com.parlakci.ciftlikbank.domain.model.Transaction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "transaction")
public class TransactionEntity extends EntityBase {

    @Column(name = "uid", nullable = false)
    private String uid;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private AccountEntity account;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "referenceUid")
    private String referenceUid;

    public Transaction toModel() {
        return new Transaction(
                this.getId(),
                this.getUid(),
                this.getAmount(),
                this.getAccount().toModel(),
                this.getReferenceUid(),
                this.getCreatedAt(),
                this.getUpdatedAt()
        );
    }
}
