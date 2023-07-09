package com.parlakci.ciftlikbank.adapter.feign;

import com.parlakci.ciftlikbank.application.port.RatePort;
import com.parlakci.ciftlikbank.domain.model.RateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@FeignClient(value = "rate-api", url = "https://v6.exchangerate-api.com")
public interface RestRateFeignClient extends RatePort {

    @Override
    @GetMapping("/v6/a45ab7342b9c8e379e58be76/latest/USD")
    RateResponse getRates();

}
