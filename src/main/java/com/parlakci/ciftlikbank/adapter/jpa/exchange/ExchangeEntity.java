package com.parlakci.ciftlikbank.adapter.jpa.exchange;

import com.parlakci.ciftlikbank.adapter.jpa.EntityBase;
import com.parlakci.ciftlikbank.adapter.jpa.account.AccountEntity;
import com.parlakci.ciftlikbank.domain.model.Exchange;
import com.parlakci.ciftlikbank.domain.model.ExchangeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "exchange")
public class ExchangeEntity extends EntityBase {
    @Column(name = "uid")
    private String uid;

    @ManyToOne
    @JoinColumn(name = "deposit_account_id", referencedColumnName = "id", nullable = false)
    private AccountEntity depositAccount;

    @ManyToOne
    @JoinColumn(name = "withdraw_account_id", referencedColumnName = "id", nullable = false)
    private AccountEntity withdrawAccount;

    @Column(name = "exchangeType", nullable = false)
    private ExchangeType exchangeType;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "rate")
    private BigDecimal rate;

    public Exchange toModel() {
        return new Exchange(
                this.getId(),
                this.getUid(),
                this.getDepositAccount().toModel(),
                this.getWithdrawAccount().toModel(),
                this.getExchangeType(),
                this.getAmount(),
                this.getRate(),
                this.getCreatedAt(),
                this.getUpdatedAt()
        );
    }
}
