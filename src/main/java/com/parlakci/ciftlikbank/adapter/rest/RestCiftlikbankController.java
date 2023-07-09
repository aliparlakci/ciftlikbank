package com.parlakci.ciftlikbank.adapter.rest;

import com.parlakci.ciftlikbank.adapter.rest.request.AccountRequest;
import com.parlakci.ciftlikbank.adapter.rest.request.DepositRequest;
import com.parlakci.ciftlikbank.adapter.rest.request.ExchangeRequest;
import com.parlakci.ciftlikbank.adapter.rest.request.WithdrawRequest;
import com.parlakci.ciftlikbank.adapter.rest.response.AccountResponse;
import com.parlakci.ciftlikbank.adapter.rest.response.TicketResponse;
import com.parlakci.ciftlikbank.application.service.*;
import com.parlakci.ciftlikbank.domain.model.Currency;
import com.parlakci.ciftlikbank.domain.model.ExchangeType;
import com.parlakci.ciftlikbank.domain.model.vo.ExchangeVo;
import com.parlakci.ciftlikbank.domain.model.vo.TicketVo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RestCiftlikbankController {

    private final AccountService accountService;
    private final MoneyManager moneyManager;
    private final TicketService ticketService;

    @PostMapping("/v1/accounts")
    public AccountResponse createAccount(@RequestBody @NotNull AccountRequest accountRequest) {
        return accountService.createAccount(accountRequest);
    }

    @GetMapping("/v1/accounts/{accountUid}")
    public AccountResponse retrieveAccount(@PathVariable String accountUid) {
        return accountService.retrieveAccount(accountUid);
    }

    @PostMapping("/v1/accounts/{accountUid}/deposit")
    public void deposit(@PathVariable String accountUid, @RequestBody @NotNull DepositRequest depositRequest) {
        moneyManager.deposit(accountUid, depositRequest.getAmount());
    }

    @PostMapping("/v1/accounts/{accountUid}/withdraw")
    public void withdraw(@PathVariable String accountUid, @RequestBody @NotNull WithdrawRequest withdrawRequest) {
        moneyManager.withdraw(accountUid, withdrawRequest.getAmount());
    }

    @PostMapping("/v1/buy-dollars")
    public void buyDollars(@RequestBody ExchangeRequest exchangeRequest) {
        ExchangeVo exchangeVo = ExchangeVo.from(ExchangeType.BUY_USD, exchangeRequest);
        moneyManager.exchange(exchangeVo, Currency.TRY, Currency.USD);
    }

    @PostMapping("/v1/sell-dollars")
    public void sellDollars(@RequestBody ExchangeRequest exchangeRequest) {
        ExchangeVo exchangeVo = ExchangeVo.from(ExchangeType.SELL_USD, exchangeRequest);
        moneyManager.exchange(exchangeVo, Currency.USD, Currency.TRY);
    }

    @PostMapping("/v1/exchange/ticket")
    public TicketResponse requestTicket() {
        TicketVo ticketVo = ticketService.requestTicket();
        return TicketResponse.from(ticketVo);
    }
}
