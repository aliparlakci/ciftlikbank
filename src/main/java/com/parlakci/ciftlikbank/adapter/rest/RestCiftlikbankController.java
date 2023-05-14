package com.parlakci.ciftlikbank.adapter.rest;

import com.parlakci.ciftlikbank.adapter.rest.request.AccountRequest;
import com.parlakci.ciftlikbank.adapter.rest.request.DepositRequest;
import com.parlakci.ciftlikbank.adapter.rest.request.ExchangeRequest;
import com.parlakci.ciftlikbank.adapter.rest.request.WithdrawRequest;
import com.parlakci.ciftlikbank.adapter.rest.response.AccountResponse;
import com.parlakci.ciftlikbank.adapter.rest.response.TicketResponse;
import com.parlakci.ciftlikbank.application.service.AccountService;
import com.parlakci.ciftlikbank.application.service.RateService;
import com.parlakci.ciftlikbank.domain.model.vo.ExchangeVo;
import com.parlakci.ciftlikbank.domain.model.vo.TicketVo;
import com.parlakci.ciftlikbank.application.service.BookkeepingService;
import com.parlakci.ciftlikbank.application.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RestCiftlikbankController {

    private final BookkeepingService bookkeepingService;
    private final AccountService accountService;
    private final ExchangeService exchangeService;
    private final RateService rateService;

    @PostMapping("/v1/accounts")
    public AccountResponse createAccount(@RequestBody AccountRequest accountRequest) {
        return accountService.createAccount(accountRequest);
    }

    @GetMapping("/v1/accounts/{accountUid}")
    public AccountResponse retrieveAccount(@PathVariable String accountUid) {
        return accountService.retrieveAccount(accountUid);
    }

    @PostMapping("/v1/accounts/{accountUid}/deposit")
    public void deposit(@PathVariable String accountUid, @RequestBody DepositRequest depositRequest) {
        bookkeepingService.deposit(accountUid, depositRequest.getAmount());
    }

    @PostMapping("/v1/accounts/{accountUid}/withdraw")
    public void withdraw(@PathVariable String accountUid, @RequestBody WithdrawRequest withdrawRequest) {
        bookkeepingService.withdraw(accountUid, withdrawRequest.getAmount());
    }

    @PostMapping("/v1/buy-dollars")
    public void buyDollars(@RequestBody ExchangeRequest exchangeRequest) {
        ExchangeVo exchangeVo = ExchangeVo.from(exchangeRequest);
        exchangeService.buyDollars(exchangeVo);
    }

    @PostMapping("/v1/sell-dollars")
    public void sellDollars(@RequestBody ExchangeRequest exchangeRequest) {
        ExchangeVo exchangeVo = ExchangeVo.from(exchangeRequest);
        exchangeService.sellDollars(exchangeVo);
    }

    @PostMapping("/v1/exchange/ticket")
    public TicketResponse requestTicket() {
        TicketVo ticketVo = rateService.requestTicket();
        return TicketResponse.from(ticketVo);
    }
}
