package com.aray1.commonproblems.currencyconverter.service.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.aray1.commonproblems.currencyconverter.entity.ExchangeRateSearchEntity;
import com.aray1.commonproblems.currencyconverter.model.ExchangeRateModel;

@Service
public class ExchangeRateSearchEntityMapper implements Function<ExchangeRateModel, ExchangeRateSearchEntity> {
    @Override
    public ExchangeRateSearchEntity apply(ExchangeRateModel exchangeRateModel) {
        ExchangeRateSearchEntity exchangeRateSearchEntity = new ExchangeRateSearchEntity();
        exchangeRateSearchEntity.setExchangeRate(exchangeRateModel.getExchangeRate());
        exchangeRateSearchEntity.setFromCurrency(exchangeRateModel.getFromCurrency());
        exchangeRateSearchEntity.setToCurrency(exchangeRateModel.getToCurrency());
        try {
            exchangeRateSearchEntity.setExchangeRateDate(
                    new SimpleDateFormat("YYYY-MM-dd").parse(exchangeRateModel.getExchangeRateDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return exchangeRateSearchEntity;
    }
}
