package com.aray1.commonproblems.currencyconverter.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class representing the response of external currency exchange api.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyExchange {

    @JsonProperty
    private String base;

    @JsonProperty
    private Map<String, Double> rates;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }

}
