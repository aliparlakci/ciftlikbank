package com.parlakci.ciftlikbank.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RateResponse {

    @JsonProperty("conversion_rates")
    private ConversionRates conversionRates;

}
