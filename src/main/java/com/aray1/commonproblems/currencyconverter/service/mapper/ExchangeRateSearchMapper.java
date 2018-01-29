package com.aray1.commonproblems.currencyconverter.service.mapper;

import java.text.SimpleDateFormat;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aray1.commonproblems.currencyconverter.entity.ExchangeRateSearchEntity;
import com.aray1.commonproblems.currencyconverter.model.ExchangeRateModel;

/**
 * Mapper to map a {@link ExchangeRateSearchEntity} to an {@link ExchangeRateModel}.
 */
@Service
public class ExchangeRateSearchMapper implements Function<ExchangeRateSearchEntity, ExchangeRateModel> {

    private final String dateFormat;

    @Autowired
    public ExchangeRateSearchMapper(@Value("${date.format}") String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public ExchangeRateModel apply(ExchangeRateSearchEntity exchangeRateSearchEntity) {
        ExchangeRateModel exchangeRateModel = new ExchangeRateModel();
        exchangeRateModel.setExchangeRate(exchangeRateSearchEntity.getExchangeRate());
        exchangeRateModel.setFromCurrency(exchangeRateSearchEntity.getFromCurrency());
        exchangeRateModel.setToCurrency(exchangeRateSearchEntity.getToCurrency());
        exchangeRateModel.setExchangeRateDate(
                new SimpleDateFormat(dateFormat).format(exchangeRateSearchEntity.getExchangeRateDate()));
        return exchangeRateModel;
    }
}
