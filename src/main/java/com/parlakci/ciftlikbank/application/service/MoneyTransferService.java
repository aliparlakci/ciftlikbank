package com.parlakci.ciftlikbank.application.service;

import com.parlakci.ciftlikbank.application.port.ExchangePersistPort;
import com.parlakci.ciftlikbank.application.port.TransactionPersistPort;
import com.parlakci.ciftlikbank.domain.exception.BusinessException;
import com.parlakci.ciftlikbank.domain.model.vo.ExchangeVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class MoneyTransferService {

    private final BookkeepingService bookkeepingService;
    private final TransactionPersistPort transactionPersistPort;
    private final ExchangePersistPort exchangePersistPort;

    @Transactional
    public void deposit(String accountUid, BigDecimal amount) {
        ensureNonnegative(amount);
        transactionPersistPort.create(accountUid, amount);
    }

    @Transactional
    public void withdraw(String accountUid, BigDecimal amount) {
        ensureNonnegative(amount);
        BigDecimal accountBalance = bookkeepingService.retrieveAccountBalance(accountUid);
        if (amount.compareTo(accountBalance) < 0) {
            throw new BusinessException("Insufficient funds!");
        }
        transactionPersistPort.create(accountUid, amount.negate());
    }

    @Transactional
    public void exchange(ExchangeVo exchangeVo, String depositAccountUid, BigDecimal depositAmount, String withdrawAccountUid, BigDecimal withdrawAmount) {
        BigDecimal withdrawAccountBalance = bookkeepingService.retrieveAccountBalance(withdrawAccountUid);
        ensureNonnegative(withdrawAccountBalance.subtract(withdrawAmount));
        transactionPersistPort.create(withdrawAccountUid, withdrawAmount, exchangeVo.getUid());
        transactionPersistPort.create(depositAccountUid, depositAmount, exchangeVo.getUid());
        exchangePersistPort.create(exchangeVo);
    }

    private static void ensureNonnegative(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Withdraw amount cannot be negative!");
        }
    }
}
