package com.aray1.commonproblems.currencyconverter.service;

import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.aray1.commonproblems.currencyconverter.model.ExchangeRateModel;

/**
 * Service that calls an external service for currency exchange.
 */
public interface CurrencyExchangeService {

    /**
     * Provides the exchange rate for the passed base currency and the evaluating currency.
     *
     * @param baseCurrency
     *         the base currency.
     * @param evaluatingCurrency
     *         the evaluating currency.
     * @param date
     *         the requested date.
     */
    ExchangeRateModel getExchangeRate(String baseCurrency, String evaluatingCurrency, String date);

    /**
     * Default implementation to fetch all available currency codes.
     */
    default List<String> getAllCurrencies() {
        return Arrays.stream(Locale.getAvailableLocales()).filter(locale -> locale.getCountry().length() == 2)
                .map(Currency::getInstance).map(Currency::getCurrencyCode).distinct().collect(Collectors.toList());
    }

}
