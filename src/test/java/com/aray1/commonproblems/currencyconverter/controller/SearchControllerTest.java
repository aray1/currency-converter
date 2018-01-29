package com.aray1.commonproblems.currencyconverter.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.aray1.commonproblems.currencyconverter.model.ExchangeRateModel;
import com.aray1.commonproblems.currencyconverter.service.CurrencyExchangeService;
import com.aray1.commonproblems.currencyconverter.service.ExchangeRateSearchService;

@RunWith(MockitoJUnitRunner.class)
public class SearchControllerTest {

    @Mock
    private CurrencyExchangeService currencyExchangeService;
    @Mock
    private ExchangeRateSearchService exchangeRateSearchService;
    @Mock
    private Principal principal;

    @InjectMocks
    private SearchController searchController;

    @Test
    public void redirectToSearch() {
        RedirectView redirectView = searchController.redirectToSearch();

        assertEquals("Incorrect redirect url", "/search", redirectView.getUrl());
    }

    @Test
    public void initializeSearch() {
        when(currencyExchangeService.getAllCurrencies()).thenReturn(Collections.singletonList("EUR"));
        when(exchangeRateSearchService.getExchangeRateSearchesForUser(anyString()))
                .thenReturn(Collections.singletonList(new ExchangeRateModel()));

        ModelAndView modelAndView =
                searchController.initializeSearch(new ModelAndView(), new ExchangeRateModel(), principal);

        assertEquals("Incorrect view name", "search", modelAndView.getViewName());
        assertNotNull("currencies not set", modelAndView.getModel().get("currencies"));
        assertNotNull("exchangeRate not set", modelAndView.getModel().get("exchangeRate"));
        assertNotNull("exchangeRateList not set", modelAndView.getModel().get("exchangeRateList"));
    }

    @Test
    public void search() {
        String fromCur = "EUR";
        String toCur = "USD";
        String date = "2018-01-03";
        when(currencyExchangeService.getExchangeRate(eq(fromCur), eq(toCur), eq(date)))
                .thenReturn(new ExchangeRateModel());

        ModelAndView modelAndView = searchController.search(new ModelAndView(), "EUR", "USD", "2018-01-03", principal);

        assertEquals("Incorrect view name", "search:: resultsList", modelAndView.getViewName());
        assertNotNull("searchedExchangeRate not set", modelAndView.getModel().get("searchedExchangeRate"));
        verify(exchangeRateSearchService, times(1)).saveExchangeRateSearch(any(ExchangeRateModel.class), anyString());
    }

}