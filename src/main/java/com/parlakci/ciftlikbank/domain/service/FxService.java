package com.parlakci.ciftlikbank.domain.service;

import com.parlakci.ciftlikbank.application.port.TransactionPersistPort;
import com.parlakci.ciftlikbank.domain.model.vo.FxVo;
import com.parlakci.ciftlikbank.domain.model.vo.TicketVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FxService {
    private final BalanceService balanceService;
    private final TransactionPersistPort transactionPersistPort;

    public void fx(FxVo fxVo) {

    }

    public TicketVo requestTicket() {
        // TODO implement
        return TicketVo.builder().build();
    }
}
