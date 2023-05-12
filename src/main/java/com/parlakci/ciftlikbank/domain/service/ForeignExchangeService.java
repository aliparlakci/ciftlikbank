package com.parlakci.ciftlikbank.domain.service;

import com.parlakci.ciftlikbank.application.port.TransactionPersistPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ForeignExchangeService {
    private final BalanceService balanceService;
    private final TransactionPersistPort transactionPersistPort;
}
