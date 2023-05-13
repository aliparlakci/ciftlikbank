package com.parlakci.ciftlikbank.adapter.rest;

import com.parlakci.ciftlikbank.adapter.rest.request.DepositRequest;
import com.parlakci.ciftlikbank.adapter.rest.request.FxRequest;
import com.parlakci.ciftlikbank.adapter.rest.request.WithdrawRequest;
import com.parlakci.ciftlikbank.adapter.rest.response.AccountResponse;
import com.parlakci.ciftlikbank.adapter.rest.response.TicketResponse;
import com.parlakci.ciftlikbank.application.facade.AccountRetrieveFacade;
import com.parlakci.ciftlikbank.domain.model.vo.FxVo;
import com.parlakci.ciftlikbank.domain.model.vo.TicketVo;
import com.parlakci.ciftlikbank.domain.service.FxService;
import com.parlakci.ciftlikbank.domain.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RestMoneyController {

    private final TransferService transferService;
    private final AccountRetrieveFacade accountRetrieveFacade;
    private final FxService fxService;

    @PostMapping("/v1/fx")
    public void fx(@RequestBody FxRequest fxRequest) {
        fxService.fx(FxVo.from(fxRequest));
    }

    @PostMapping("/v1/fx/ticket")
    public TicketResponse requestTicket() {
        TicketVo ticketVo = fxService.requestTicket();
        return TicketResponse.from(ticketVo);
    }

    @PostMapping("/v1/accounts/{accountUid}/deposit")
    public void deposit(@PathVariable String accountUid, @RequestBody DepositRequest depositRequest) {
        transferService.deposit(accountUid, depositRequest.getAmount());
    }

    @PostMapping("/v1/accounts/{accountUid}/withdraw")
    public void withdraw(@PathVariable String accountUid, @RequestBody WithdrawRequest withdrawRequest) {
        transferService.withdraw(accountUid, withdrawRequest.getAmount());
    }

    @GetMapping("/v1/accounts/{accountUid}")
    public AccountResponse retrieveAccount(@PathVariable String accountUid) {
        return accountRetrieveFacade.retrieveAccount(accountUid);
    }
}