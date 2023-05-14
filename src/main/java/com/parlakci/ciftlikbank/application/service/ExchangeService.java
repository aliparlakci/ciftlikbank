package com.parlakci.ciftlikbank.application.service;

import com.parlakci.ciftlikbank.application.port.AccountPersistPort;
import com.parlakci.ciftlikbank.application.port.TicketCachePort;
import com.parlakci.ciftlikbank.domain.exception.BusinessException;
import com.parlakci.ciftlikbank.domain.model.*;
import com.parlakci.ciftlikbank.domain.model.vo.ExchangeVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeService {
    private final AccountPersistPort accountPersistPort;
    private final TicketCachePort ticketCachePort;
    private final MoneyTransferService moneyTransferService;

    public void exchange(ExchangeVo exchangeVo, Currency withdrawCurrency, Currency depositCurrency) {
        BigDecimal rate = ticketCachePort.retrieveTicket(exchangeVo.getTicket())
                .orElseThrow(() -> new BusinessException("Ticket has expired!"));

        Account depositAccount = accountPersistPort.retrieveAccountByUid(exchangeVo.getDepositAccountUid());
        ensureAccountCurrency(depositAccount, depositCurrency);

        Account withdrawAccount = accountPersistPort.retrieveAccountByUid(exchangeVo.getWithdrawAccountUid());
        ensureAccountCurrency(withdrawAccount, withdrawCurrency);

        BigDecimal depositAmount = calculateAmountInCurrency(depositCurrency, exchangeVo.getAmount(), rate);
        BigDecimal withdrawAmount = calculateAmountInCurrency(withdrawCurrency, exchangeVo.getAmount(), rate);

        exchangeVo.setUid(UUID.randomUUID().toString());
        moneyTransferService.exchange(exchangeVo, depositAccount.uid(), depositAmount, withdrawAccount.uid(), withdrawAmount);
    }

    private static void ensureAccountCurrency(Account account, Currency expectedCurrency) {
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
