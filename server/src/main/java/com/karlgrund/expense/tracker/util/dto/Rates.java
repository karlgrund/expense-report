package com.karlgrund.expense.tracker.util.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rates {
    private Double eur, hrk;

    @JsonCreator
    public Rates(
        @JsonProperty("EUR") Double eur,
        @JsonProperty("HKR") Double hrk
    ) {
        this.eur = eur;
        this.hrk = hrk;
    }

    public Double getEur() {
        return eur;
    }

    public Double getHrk() {
        return hrk;
    }
}
