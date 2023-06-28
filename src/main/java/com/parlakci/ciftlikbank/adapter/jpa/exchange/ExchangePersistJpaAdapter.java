package com.parlakci.ciftlikbank.adapter.jpa.exchange;

import com.parlakci.ciftlikbank.adapter.jpa.account.AccountEntity;
import com.parlakci.ciftlikbank.adapter.jpa.account.AccountRepository;
import com.parlakci.ciftlikbank.application.port.ExchangePersistPort;
import com.parlakci.ciftlikbank.domain.model.vo.ExchangeVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangePersistJpaAdapter implements ExchangePersistPort {

    private final ExchangeRepository exchangeRepository;
    private final AccountRepository accountRepository;

    public void create(ExchangeVo exchangeVo, BigDecimal rate) {
        AccountEntity depositAccount = accountRepository.findByUid(exchangeVo.getDepositAccountUid());
        AccountEntity withdrawAccount = accountRepository.findByUid(exchangeVo.getWithdrawAccountUid());

        ExchangeEntity exchangeEntity = new ExchangeEntity();

        exchangeEntity.setUid(exchangeVo.getUid());
        exchangeEntity.setExchangeType(exchangeVo.getExchangeType());
        exchangeEntity.setRate(rate);
        exchangeEntity.setAmount(exchangeVo.getAmount());
        exchangeEntity.setDepositAccount(depositAccount);
        exchangeEntity.setWithdrawAccount(withdrawAccount);
        exchangeEntity.setCreatedAt(ZonedDateTime.now());

        exchangeRepository.saveAndFlush(exchangeEntity);
    }
}
