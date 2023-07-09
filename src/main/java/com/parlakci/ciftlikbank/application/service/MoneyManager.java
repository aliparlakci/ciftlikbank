package com.parlakci.ciftlikbank.application.service;

import com.parlakci.ciftlikbank.application.port.AccountPersistPort;
import com.parlakci.ciftlikbank.application.port.LockPort;
import com.parlakci.ciftlikbank.application.port.TicketPersistPort;
import com.parlakci.ciftlikbank.domain.exception.BusinessException;
import com.parlakci.ciftlikbank.domain.model.*;
import com.parlakci.ciftlikbank.domain.model.vo.ExchangeVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class MoneyManager {

    private final AccountPersistPort accountPersistPort;
    private final TicketPersistPort ticketCachePort;
    private final BookkeepingService bookkeepingService;
    private final LockPort lockPort;

    public void deposit(String accountUid, BigDecimal amount) {
        lockPort.tryLock(accountUid);
        try {
            bookkeepingService.deposit(accountUid, amount);
        } finally {
            lockPort.unlock(accountUid);
        }
    }

    public void withdraw(String accountUid, BigDecimal amount) {
        lockPort.tryLock(accountUid);
        try {
            bookkeepingService.withdraw(accountUid, amount);
        } finally {
            lockPort.unlock(accountUid);
        }
    }

    public void exchange(ExchangeVo exchangeVo, Currency withdrawCurrency, Currency depositCurrency) {
        lockPort.tryLock(exchangeVo.getDepositAccountUid(), exchangeVo.getWithdrawAccountUid());
        try {
            BigDecimal rate = ticketCachePort.retrieveTicket(exchangeVo.getTicket())
                    .orElseThrow(() -> new BusinessException("Ticket has expired!"));

            Account depositAccount = accountPersistPort.retrieveAccountByUid(exchangeVo.getDepositAccountUid());
            validateAccountCurrency(depositAccount, depositCurrency);

            Account withdrawAccount = accountPersistPort.retrieveAccountByUid(exchangeVo.getWithdrawAccountUid());
            validateAccountCurrency(withdrawAccount, withdrawCurrency);

            BigDecimal depositAmount = calculateAmountInCurrency(depositCurrency, exchangeVo.getAmount(), rate);
            BigDecimal withdrawAmount = calculateAmountInCurrency(withdrawCurrency, exchangeVo.getAmount(), rate);

            bookkeepingService.exchange(exchangeVo, depositAmount, withdrawAmount, rate);
        } finally {
            lockPort.unlock(exchangeVo.getDepositAccountUid(), exchangeVo.getWithdrawAccountUid());
        }
    }

    private static void validateAccountCurrency(Account account, Currency expectedCurrency) {
        if (!expectedCurrency.equals(account.currency())) {
            throw new BusinessException("Account type mismatch!");
        }
    }

    private static BigDecimal calculateAmountInCurrency(Currency currency, BigDecimal amount, BigDecimal rate) {
        return Currency.USD.equals(currency)
                ? amount
                : rate.multiply(amount);
    }
}
