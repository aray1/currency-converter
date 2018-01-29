package com.aray1.commonproblems.currencyconverter.service.mapper;

import java.text.SimpleDateFormat;
import java.util.function.Function;

import com.aray1.commonproblems.currencyconverter.entity.ExchangeRateSearchEntity;
import com.aray1.commonproblems.currencyconverter.model.ExchangeRateModel;

public class ExchangeRateSearchMapper implements Function<ExchangeRateSearchEntity, ExchangeRateModel> {
    @Override
    public ExchangeRateModel apply(ExchangeRateSearchEntity exchangeRateSearchEntity) {
        ExchangeRateModel exchangeRateModel = new ExchangeRateModel();
        exchangeRateModel.setExchangeRate(exchangeRateSearchEntity.getExchangeRate());
        exchangeRateModel.setFromCurrency(exchangeRateSearchEntity.getFromCurrency());
        exchangeRateModel.setToCurrency(exchangeRateSearchEntity.getToCurrency());
        exchangeRateModel.setExchangeRateDate(
                new SimpleDateFormat("YYYY-MM-dd").format(exchangeRateSearchEntity.getExchangeRateDate()));
        return exchangeRateModel;
    }
}
