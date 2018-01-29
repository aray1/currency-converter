package com.aray1.commonproblems.currencyconverter.service.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aray1.commonproblems.currencyconverter.entity.ExchangeRateSearchEntity;
import com.aray1.commonproblems.currencyconverter.model.ExchangeRateModel;

/**
 * Mapper to map a {@link ExchangeRateModel} to an {@link ExchangeRateSearchEntity}.
 */
@Service
public class ExchangeRateSearchEntityMapper implements Function<ExchangeRateModel, ExchangeRateSearchEntity> {

    private final String dateFormat;

    @Autowired
    public ExchangeRateSearchEntityMapper(@Value("${date.format}") String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public ExchangeRateSearchEntity apply(ExchangeRateModel exchangeRateModel) {
        ExchangeRateSearchEntity exchangeRateSearchEntity = new ExchangeRateSearchEntity();
        exchangeRateSearchEntity.setExchangeRate(exchangeRateModel.getExchangeRate());
        exchangeRateSearchEntity.setFromCurrency(exchangeRateModel.getFromCurrency());
        exchangeRateSearchEntity.setToCurrency(exchangeRateModel.getToCurrency());
        try {
            exchangeRateSearchEntity.setExchangeRateDate(
                    new SimpleDateFormat(dateFormat).parse(exchangeRateModel.getExchangeRateDate()));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Incorrect Date");
        }
        return exchangeRateSearchEntity;
    }

}
