package com.aray1.commonproblems.currencyconverter.service;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import com.aray1.commonproblems.currencyconverter.model.CurrencyExchange;
import com.aray1.commonproblems.currencyconverter.model.ExchangeRateModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@RestClientTest(FixerCurrencyExchangeService.class)
public class FixerCurrencyExchangeServiceTest {

    @Autowired
    private FixerCurrencyExchangeService fixerCurrencyExchangeService;

    @Autowired
    private MockRestServiceServer server;

    @Test
    public void getExchangeRate() throws JsonProcessingException {
        //given
        CurrencyExchange currencyExchange = new CurrencyExchange();
        currencyExchange.setBase("EUR");
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 1.7);
        currencyExchange.setRates(rates);
        server.expect(requestTo("http://api.fixer.io/2018-01-05?base=EUR&symbols=USD")).andRespond(
                withSuccess(new ObjectMapper().writeValueAsString(currencyExchange), MediaType.APPLICATION_JSON));

        //when
        ExchangeRateModel exchangeRateModel = fixerCurrencyExchangeService.getExchangeRate("EUR", "USD", "2018-01-05");

        //then
        assertEquals("Incorrect from currency", "EUR", exchangeRateModel.getFromCurrency());
        assertEquals("Incorrect to currency", "USD", exchangeRateModel.getToCurrency());
        assertEquals("Incorrect exchange rate", new Double(1.7), exchangeRateModel.getExchangeRate());
        assertEquals("Incorrect exchange rate date", "2018-01-05", exchangeRateModel.getExchangeRateDate());
    }

}