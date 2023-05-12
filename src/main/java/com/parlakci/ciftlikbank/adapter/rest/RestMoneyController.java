package com.parlakci.ciftlikbank.adapter.rest;

import com.parlakci.ciftlikbank.adapter.rest.request.DepositRequest;
import com.parlakci.ciftlikbank.adapter.rest.request.WithdrawRequest;
import com.parlakci.ciftlikbank.domain.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RestMoneyController {

    private final TransferService transferService;

    @GetMapping("/v1/internal/accounts/{accountUid}/deposit")
    public void deposit(@PathVariable String accountUid, @RequestBody DepositRequest depositRequest) {
        transferService.deposit(accountUid, depositRequest.getAmount());
    }

    @GetMapping("/v1/internal/accounts/{accountUid}/withdraw")
    public void withdraw(@PathVariable String accountUid, @RequestBody WithdrawRequest withdrawRequest) {
        transferService.withdraw(accountUid, withdrawRequest.getAmount());
    }
}
