package com.aray1.commonproblems.currencyconverter.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.aray1.commonproblems.currencyconverter.model.CurrencyExchange;
import com.aray1.commonproblems.currencyconverter.model.ExchangeRateModel;

@Component("fixer")
public class FixerCurrencyExchangeService implements CurrencyExchangeService {

    private final String currencyExchangePath;
    private final RestTemplate restTemplate;

    @Autowired
    public FixerCurrencyExchangeService(@Value("${currency.exchange.api.path}") String currencyExchangePath,
            RestTemplateBuilder restTemplateBuilder) {
        this.currencyExchangePath = currencyExchangePath;
        this.restTemplate = restTemplateBuilder.build();
    }

    public ExchangeRateModel getExchangeRate(final String baseCurrency, final String evaluatingCurrency,
            final String date) {
        Objects.requireNonNull(baseCurrency);
        Objects.requireNonNull(evaluatingCurrency);
        String uri = String.format(currencyExchangePath + "/%s?base=%s&symbols=%s", date, baseCurrency.toUpperCase(),
                evaluatingCurrency.toUpperCase());
        ResponseEntity<CurrencyExchange> forEntity = restTemplate.getForEntity(uri, CurrencyExchange.class);

        ExchangeRateModel exchangeRateModel = new ExchangeRateModel();
        exchangeRateModel.setFromCurrency(baseCurrency);
        exchangeRateModel.setToCurrency(evaluatingCurrency);
        exchangeRateModel.setExchangeRateDate(date);
        exchangeRateModel.setExchangeRate(forEntity.getBody().getRates().get(evaluatingCurrency));
        return exchangeRateModel;
    }

}
