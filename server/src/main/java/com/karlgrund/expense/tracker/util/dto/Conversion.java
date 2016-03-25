package com.karlgrund.expense.tracker.util.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.karlgrund.expense.tracker.util.CurrencyId;
import java.util.Date;

public class Conversion {
    private String base;
    private Date date;
    private Rates rates;

    @JsonCreator
    public Conversion(
        @JsonProperty("base") String base,
        @JsonProperty("date") Date date,
        @JsonProperty("rates") Rates rates
    ) {
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    public Double convert(Long amount, CurrencyId id) {
        switch (id) {
            case SEK:
                return amount * 1.0;
            case HRK:
                return amount / rates.getHrk();
            case EUR:
                return amount / rates.getEur();
            default:
                return 0.0;
        }
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setRates(Rates rates) {
        this.rates = rates;
    }
}


