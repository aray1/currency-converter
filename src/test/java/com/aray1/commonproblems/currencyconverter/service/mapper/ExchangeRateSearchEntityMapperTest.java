package com.aray1.commonproblems.currencyconverter.service.mapper;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;

import org.junit.Test;

import com.aray1.commonproblems.currencyconverter.entity.ExchangeRateSearchEntity;
import com.aray1.commonproblems.currencyconverter.model.ExchangeRateModel;

public class ExchangeRateSearchEntityMapperTest {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private final ExchangeRateSearchEntityMapper exchangeRateSearchEntityMapper =
            new ExchangeRateSearchEntityMapper(simpleDateFormat.toPattern());

    @Test
    public void mapToExchangeRateSearchEntity() {
        //given
        ExchangeRateModel exchangeRateModel = euroToInrExchangeRateModel();

        //when
        ExchangeRateSearchEntity exchangeRateSearchEntity = exchangeRateSearchEntityMapper.apply(exchangeRateModel);

        //then
        assertEquals("Incorrect from currency", exchangeRateModel.getFromCurrency(),
                exchangeRateSearchEntity.getFromCurrency());
        assertEquals("Incorrect to currency", exchangeRateModel.getToCurrency(),
                exchangeRateSearchEntity.getToCurrency());
        assertEquals("Incorrect exchange rate", exchangeRateModel.getExchangeRate(),
                exchangeRateSearchEntity.getExchangeRate());
        assertEquals("Incorrect exchange rate date", exchangeRateModel.getExchangeRateDate(),
                simpleDateFormat.format(exchangeRateSearchEntity.getExchangeRateDate()));
    }

    private ExchangeRateModel euroToInrExchangeRateModel() {
        ExchangeRateModel exchangeRateModel = new ExchangeRateModel();
        exchangeRateModel.setFromCurrency("EUR");
        exchangeRateModel.setToCurrency("INR");
        exchangeRateModel.setExchangeRate(79.6);
        exchangeRateModel.setExchangeRateDate("2018-01-28");
        return exchangeRateModel;
    }

}