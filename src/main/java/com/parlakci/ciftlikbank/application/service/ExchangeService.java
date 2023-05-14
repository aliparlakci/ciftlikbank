package com.parlakci.ciftlikbank.application.service;

import com.parlakci.ciftlikbank.application.port.AccountPersistPort;
import com.parlakci.ciftlikbank.application.port.ExchangePersistPort;
import com.parlakci.ciftlikbank.application.port.TicketCachePort;
import com.parlakci.ciftlikbank.domain.exception.BusinessException;
import com.parlakci.ciftlikbank.domain.model.*;
import com.parlakci.ciftlikbank.domain.model.vo.ExchangeVo;
import com.parlakci.ciftlikbank.domain.model.vo.TicketVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeService {
    private final BalanceService balanceService;
    private final ExchangePersistPort exchangePersistPort;
    private final AccountPersistPort accountPersistPort;
    private final TicketCachePort ticketCachePort;

    public void buyDollars(ExchangeVo exchangeRequestVo) {
        Account depositAccount = accountPersistPort.retrieveAccountByUid(exchangeRequestVo.depositAccountUid());
        this.ensureAccountCurrency(depositAccount, Currency.USD);

        Account withdrawAccount = accountPersistPort.retrieveAccountByUid(exchangeRequestVo.withdrawAccountUid());
        this.ensureAccountCurrency(withdrawAccount, Currency.TRY);

        BigDecimal rate = ticketCachePort.retrieveTicket(exchangeRequestVo.ticket())
                .orElseThrow(() -> new BusinessException("Ticket has expired!"));

        BigDecimal withdrawAmount = rate.multiply(exchangeRequestVo.amount());
        BigDecimal depositAmount = exchangeRequestVo.amount();
        BigDecimal withdrawAccountBalance = balanceService.retrieveAccountBalance(exchangeRequestVo.withdrawAccountUid());
        if (withdrawAccountBalance.compareTo(withdrawAmount) < 0) {
            throw new BusinessException("Insufficient funds!");
        }

        String referenceUid = UUID.randomUUID().toString();
        Exchange exchange = new Exchange(
                null,
                referenceUid,
                depositAccount,
                withdrawAccount,
                ExchangeType.BUY_USD,
                exchangeRequestVo.amount(),
                rate,
                null,
                null);

        balanceService.exchangeDeposit(depositAccount.uid(), depositAmount, referenceUid);
        balanceService.exchangeWithdraw(withdrawAccount.uid(), withdrawAmount, referenceUid);
        exchangePersistPort.newExchange(exchange);
    }

    public void sellDollars(ExchangeVo exchangeRequestVo) {
        Account depositAccount = accountPersistPort.retrieveAccountByUid(exchangeRequestVo.depositAccountUid());
        this.ensureAccountCurrency(depositAccount, Currency.TRY);

        Account withdrawAccount = accountPersistPort.retrieveAccountByUid(exchangeRequestVo.withdrawAccountUid());
        this.ensureAccountCurrency(withdrawAccount, Currency.USD);

        BigDecimal rate = ticketCachePort.retrieveTicket(exchangeRequestVo.ticket())
                .orElseThrow(() -> new BusinessException("Ticket has expired!"));

        BigDecimal withdrawAmount = exchangeRequestVo.amount();
        BigDecimal depositAmount = rate.multiply(exchangeRequestVo.amount());
        BigDecimal withdrawAccountBalance = balanceService.retrieveAccountBalance(exchangeRequestVo.withdrawAccountUid());
        if (withdrawAccountBalance.compareTo(withdrawAmount) < 0) {
            throw new BusinessException("Insufficient funds!");
        }

        String referenceUid = UUID.randomUUID().toString();
        Exchange exchange = new Exchange(
                null,
                referenceUid,
                depositAccount,
                withdrawAccount,
                ExchangeType.SELL_USD,
                exchangeRequestVo.amount(),
                rate,
                null,
                null
        );

        balanceService.exchangeDeposit(depositAccount.uid(), depositAmount, referenceUid);
        balanceService.exchangeWithdraw(withdrawAccount.uid(), withdrawAmount, referenceUid);
        exchangePersistPort.newExchange(exchange);
    }

    public TicketVo requestTicket() {
        BigDecimal rate = requestNewRate();
        String ticket = UUID.randomUUID().toString();
        ticketCachePort.saveTicket(ticket, rate);

        return null;
    }

    private static BigDecimal requestNewRate() {
        // TODO implement
        return new BigDecimal(0);
    }

    private void ensureAccountCurrency(Account account, Currency expectedCurrency) {
        if (!expectedCurrency.equals(account.currency())) {
            throw new BusinessException("Account type mismatch!");
        }
    }
}
