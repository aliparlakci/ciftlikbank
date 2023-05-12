package com.parlakci.ciftlikbank.domain.service;

import com.parlakci.ciftlikbank.application.port.TransactionPersistPort;
import com.parlakci.ciftlikbank.domain.exception.BusinessException;
import com.parlakci.ciftlikbank.domain.model.TransactionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransactionPersistPort transactionPersistPort;
    private final BalanceService balanceService;

    public void deposit(String accountUid, BigDecimal amount) {
        // TODO locks
        // TODO invalidate cache
        transactionPersistPort.create(accountUid, amount, TransactionType.TRANSFER);
    }

    public void withdraw(String accountUid, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Withdraw amount cannot be negative!");
        }
        // TODO locks
        BigDecimal accountBalance = balanceService.retrieveAccountBalance(accountUid);
        if (accountBalance.compareTo(amount) < 0) {
            throw new BusinessException("Insufficient funds!");
        }
        transactionPersistPort.create(accountUid, amount, TransactionType.TRANSFER);
    }
}
