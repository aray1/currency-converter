package com.aray1.commonproblems.currencyconverter.service.mapper;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.aray1.commonproblems.currencyconverter.entity.ExchangeRateSearchEntity;
import com.aray1.commonproblems.currencyconverter.model.ExchangeRateModel;

public class ExchangeRateSearchMapperTest {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private final ExchangeRateSearchMapper exchangeRateSearchMapper =
            new ExchangeRateSearchMapper(simpleDateFormat.toPattern());

    @Test
    public void mapToExchangeRateModel() {
        //given
        ExchangeRateSearchEntity exchangeRateSearchEntity = euroToInrExchangeRateSearchEntity();

        //when
        ExchangeRateModel exchangeRateModel = exchangeRateSearchMapper.apply(exchangeRateSearchEntity);

        //then
        assertEquals("Incorrect from currency", exchangeRateSearchEntity.getFromCurrency(),
                exchangeRateModel.getFromCurrency());
        assertEquals("Incorrect to currency", exchangeRateSearchEntity.getToCurrency(),
                exchangeRateModel.getToCurrency());
        assertEquals("Incorrect exchange rate", exchangeRateSearchEntity.getExchangeRate(),
                exchangeRateModel.getExchangeRate());
        assertEquals("Incorrect exchange rate date",
                simpleDateFormat.format(exchangeRateSearchEntity.getExchangeRateDate()),
                exchangeRateModel.getExchangeRateDate());
    }

    private ExchangeRateSearchEntity euroToInrExchangeRateSearchEntity() {
        ExchangeRateSearchEntity exchangeRateSearchEntity = new ExchangeRateSearchEntity();
        exchangeRateSearchEntity.setFromCurrency("EUR");
        exchangeRateSearchEntity.setToCurrency("INR");
        exchangeRateSearchEntity.setExchangeRate(79.6);
        exchangeRateSearchEntity.setExchangeRateDate(new Date());
        return exchangeRateSearchEntity;
    }

}