package com.parlakci.ciftlikbank.adapter.jpa.accountSnapshot;

import com.parlakci.ciftlikbank.adapter.jpa.EntityBase;
import com.parlakci.ciftlikbank.adapter.jpa.account.AccountEntity;
import com.parlakci.ciftlikbank.domain.model.Account;
import com.parlakci.ciftlikbank.domain.model.AccountSnapshot;
import com.parlakci.ciftlikbank.domain.model.Currency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "account_snapshot")
public class AccountSnapshotEntity extends EntityBase {

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private AccountEntity account;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    public AccountSnapshot toModel() {
        return new AccountSnapshot(
                this.getId(),
                this.getAccount().toModel(),
                this.getBalance(),
                this.getCreatedAt(),
                this.getUpdatedAt()
        );
    }
}
