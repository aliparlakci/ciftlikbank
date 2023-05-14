package com.parlakci.ciftlikbank.application.service;

import com.parlakci.ciftlikbank.application.port.AccountPersistPort;
import com.parlakci.ciftlikbank.application.port.ExchangePersistPort;
import com.parlakci.ciftlikbank.application.port.TicketCachePort;
import com.parlakci.ciftlikbank.domain.exception.BusinessException;
import com.parlakci.ciftlikbank.domain.model.*;
import com.parlakci.ciftlikbank.domain.model.vo.ExchangeVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeService {
    private final BookkeepingService bookkeepingService;
    private final ExchangePersistPort exchangePersistPort;
    private final AccountPersistPort accountPersistPort;
    private final TicketCachePort ticketCachePort;

    public void buyDollars(ExchangeVo exchangeRequestVo) {
        Account depositAccount = accountPersistPort.retrieveAccountByUid(exchangeRequestVo.depositAccountUid());
        ensureAccountCurrency(depositAccount, Currency.USD);

        Account withdrawAccount = accountPersistPort.retrieveAccountByUid(exchangeRequestVo.withdrawAccountUid());
        ensureAccountCurrency(withdrawAccount, Currency.TRY);

        BigDecimal rate = ticketCachePort.retrieveTicket(exchangeRequestVo.ticket())
                .orElseThrow(() -> new BusinessException("Ticket has expired!"));

        BigDecimal withdrawAmountInLiras = rate.multiply(exchangeRequestVo.amount());
        BigDecimal depositAmountInDollars = exchangeRequestVo.amount();
        BigDecimal withdrawAccountBalance = bookkeepingService.retrieveAccountBalance(exchangeRequestVo.withdrawAccountUid());
        if (withdrawAccountBalance.compareTo(withdrawAmountInLiras) < 0) {
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

        this.exchange(
                exchange,
                depositAccount.uid(),
                depositAmountInDollars,
                withdrawAccount.uid(),
                withdrawAmountInLiras
        );
    }

    public void sellDollars(ExchangeVo exchangeRequestVo) {
        Account depositAccount = accountPersistPort.retrieveAccountByUid(exchangeRequestVo.depositAccountUid());
        ensureAccountCurrency(depositAccount, Currency.TRY);

        Account withdrawAccount = accountPersistPort.retrieveAccountByUid(exchangeRequestVo.withdrawAccountUid());
        ensureAccountCurrency(withdrawAccount, Currency.USD);

        BigDecimal rate = ticketCachePort.retrieveTicket(exchangeRequestVo.ticket())
                .orElseThrow(() -> new BusinessException("Ticket has expired!"));

        BigDecimal withdrawAmountInDollars = exchangeRequestVo.amount();
        BigDecimal depositAmountInLiras = rate.multiply(exchangeRequestVo.amount());
        BigDecimal withdrawAccountBalance = bookkeepingService.retrieveAccountBalance(exchangeRequestVo.withdrawAccountUid());
        if (withdrawAccountBalance.compareTo(withdrawAmountInDollars) < 0) {
            throw new BusinessException("Insufficient funds!");
        }

        Exchange exchange = new Exchange(
                null,
                UUID.randomUUID().toString(),
                depositAccount,
                withdrawAccount,
                ExchangeType.SELL_USD,
                exchangeRequestVo.amount(),
                rate,
                null,
                null
        );

        this.exchange(
                exchange,
                depositAccount.uid(),
                depositAmountInLiras,
                withdrawAccount.uid(),
                withdrawAmountInDollars
        );
    }

    @Transactional
    public void exchange(Exchange exchange, String depositAccountUid, BigDecimal depositAmount, String withdrawAccountUid, BigDecimal withdrawAmount) {
        bookkeepingService.exchangeDeposit(depositAccountUid, depositAmount, exchange.uid());
        bookkeepingService.exchangeWithdraw(withdrawAccountUid, withdrawAmount, exchange.uid());
        exchangePersistPort.newExchange(exchange);
    }

    private static void ensureAccountCurrency(Account account, Currency expectedCurrency) {
        if (!expectedCurrency.equals(account.currency())) {
            throw new BusinessException("Account type mismatch!");
        }
    }
}
