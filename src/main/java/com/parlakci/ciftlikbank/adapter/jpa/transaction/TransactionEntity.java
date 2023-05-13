package com.parlakci.ciftlikbank.adapter.jpa.transaction;

import com.parlakci.ciftlikbank.adapter.jpa.EntityBase;
import com.parlakci.ciftlikbank.adapter.jpa.account.AccountEntity;
import com.parlakci.ciftlikbank.domain.model.Account;
import com.parlakci.ciftlikbank.domain.model.Currency;
import com.parlakci.ciftlikbank.domain.model.Transaction;
import com.parlakci.ciftlikbank.domain.model.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(schema = "transaction")
public class TransactionEntity extends EntityBase {

    @Column(name = "uid", nullable = false)
    private String uid;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private AccountEntity account;

    @Column(name = "transaction_type")
    private TransactionType transactionType;

    @Column(name = "referenceUid")
    private String referenceUid;

    public Transaction toModel() {
        return new Transaction(
                this.getId(),
                this.getUid(),
                this.getAmount(),
                this.getAccount().toModel(),
                this.getTransactionType(),
                this.getReferenceUid(),
                this.getCreatedAt(),
                this.getUpdatedAt()
        );
    }
}