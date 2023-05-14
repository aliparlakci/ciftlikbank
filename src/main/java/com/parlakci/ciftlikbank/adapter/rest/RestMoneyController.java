package com.parlakci.ciftlikbank.adapter.rest;

import com.parlakci.ciftlikbank.adapter.rest.request.DepositRequest;
import com.parlakci.ciftlikbank.adapter.rest.request.ExchangeRequest;
import com.parlakci.ciftlikbank.adapter.rest.request.WithdrawRequest;
import com.parlakci.ciftlikbank.adapter.rest.response.AccountResponse;
import com.parlakci.ciftlikbank.adapter.rest.response.TicketResponse;
import com.parlakci.ciftlikbank.application.facade.AccountRetrieveFacade;
import com.parlakci.ciftlikbank.domain.model.vo.ExchangeVo;
import com.parlakci.ciftlikbank.domain.model.vo.TicketVo;
import com.parlakci.ciftlikbank.application.service.BalanceService;
import com.parlakci.ciftlikbank.application.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RestMoneyController {

    private final BalanceService balanceService;
    private final AccountRetrieveFacade accountRetrieveFacade;
    private final ExchangeService exchangeService;

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
        TicketVo ticketVo = exchangeService.requestTicket();
        return TicketResponse.from(ticketVo);
    }

    @GetMapping("/v1/accounts/{accountUid}")
    public AccountResponse retrieveAccount(@PathVariable String accountUid) {
        return accountRetrieveFacade.retrieveAccount(accountUid);
    }

    @PostMapping("/v1/accounts/{accountUid}/deposit")
    public void deposit(@PathVariable String accountUid, @RequestBody DepositRequest depositRequest) {
        balanceService.deposit(accountUid, depositRequest.getAmount());
    }

    @PostMapping("/v1/accounts/{accountUid}/withdraw")
    public void withdraw(@PathVariable String accountUid, @RequestBody WithdrawRequest withdrawRequest) {
        balanceService.withdraw(accountUid, withdrawRequest.getAmount());
    }
}