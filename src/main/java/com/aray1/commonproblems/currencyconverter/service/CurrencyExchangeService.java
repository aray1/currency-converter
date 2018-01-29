package com.aray1.commonproblems.currencyconverter.service;

import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.aray1.commonproblems.currencyconverter.model.CurrencyExchange;
import com.aray1.commonproblems.currencyconverter.model.ExchangeRateModel;

@Service
public class CurrencyExchangeService {

    @Value("${currency.exchange.api.path}")
    private String currencyExchangePath;

    private final RestTemplate restTemplate;

    @Autowired
    public CurrencyExchangeService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public ExchangeRateModel getExchangeRate(final String baseCurrency, final String evaluatingCurrency,
            final String date) {
        Objects.requireNonNull(baseCurrency);
        Objects.requireNonNull(evaluatingCurrency);
        String symbols = baseCurrency.toUpperCase() + "," + evaluatingCurrency.toUpperCase();
        ResponseEntity<CurrencyExchange> forEntity = restTemplate
                .getForEntity(String.format(currencyExchangePath + "/%s?symbols=%s", date, symbols),
                        CurrencyExchange.class);

        ExchangeRateModel exchangeRateModel = new ExchangeRateModel();
        exchangeRateModel.setFromCurrency(baseCurrency);
        exchangeRateModel.setToCurrency(evaluatingCurrency);
        exchangeRateModel.setExchangeRateDate(date);
        exchangeRateModel.setExchangeRate(forEntity.getBody().getRates().get(evaluatingCurrency));
        return exchangeRateModel;
    }

    public List<String> getAllCurrencies() {
        return Arrays.stream(Locale.getAvailableLocales()).filter(locale -> locale.getCountry().length() == 2)
                .map(Currency::getInstance).map(Currency::getCurrencyCode).collect(Collectors.toList());
    }

}
