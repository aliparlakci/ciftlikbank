package com.parlakci.ciftlikbank.adapter.jpa.fx;

import com.parlakci.ciftlikbank.adapter.jpa.EntityBase;
import com.parlakci.ciftlikbank.adapter.jpa.account.AccountEntity;
import com.parlakci.ciftlikbank.domain.model.Currency;
import com.parlakci.ciftlikbank.domain.model.Fx;
import com.parlakci.ciftlikbank.domain.model.FxType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(schema = "fx")
public class FxEntity extends EntityBase {
    @Column(name = "uid")
    private String uid;

    @ManyToOne
    @JoinColumn(name = "deposit_account_id", referencedColumnName = "id", nullable = false)
    private AccountEntity depositAccount;

    @ManyToOne
    @JoinColumn(name = "withdraw_account_id", referencedColumnName = "id", nullable = false)
    private AccountEntity withdrawAccount;

    @Column(name = "fxType", nullable = false)
    private FxType fxType;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "rate")
    private BigDecimal rate;

    public Fx toModel() {
        return new Fx(
                this.getId(),
                this.getUid(),
                this.getDepositAccount().toModel(),
                this.getWithdrawAccount().toModel(),
                this.getFxType(),
                this.getAmount(),
                this.getRate(),
                this.getCreatedAt(),
                this.getUpdatedAt()
        );
    }
}
