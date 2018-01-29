package com.aray1.commonproblems.currencyconverter.controller;

import static com.aray1.commonproblems.currencyconverter.ApplicationConstants.SEARCH;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.aray1.commonproblems.currencyconverter.model.ExchangeRateModel;
import com.aray1.commonproblems.currencyconverter.service.CurrencyExchangeService;
import com.aray1.commonproblems.currencyconverter.service.ExchangeRateSearchService;

/**
 * Controller to handle searching of exchange rates.
 */
@Controller
public class SearchController {

    private final CurrencyExchangeService currencyExchangeService;
    private final ExchangeRateSearchService exchangeRateSearchService;

    @Autowired
    public SearchController(@Qualifier("fixer") final CurrencyExchangeService currencyExchangeService,
            final ExchangeRateSearchService exchangeRateSearchService) {
        this.currencyExchangeService = currencyExchangeService;
        this.exchangeRateSearchService = exchangeRateSearchService;
    }

    @GetMapping("/")
    public RedirectView redirectWithUsingRedirectView(RedirectAttributes attributes) {
        return new RedirectView("/" + SEARCH);
    }

    @RequestMapping(value = "/" + SEARCH, method = RequestMethod.GET)
    public ModelAndView initializeSearch(ModelAndView modelAndView, ExchangeRateModel exchangeRateModel,
            Principal principal) {
        modelAndView.addObject("currencies", currencyExchangeService.getAllCurrencies());
        modelAndView.addObject("exchangeRate", exchangeRateModel);
        modelAndView.addObject("exchangeRateList",
                exchangeRateSearchService.getExchangeRateSearchesForUser(principal.getName()));
        modelAndView.setViewName(SEARCH);
        return modelAndView;
    }

    @RequestMapping(value = "/search/{from}/{to}/{date}", method = RequestMethod.GET)
    public ModelAndView search(ModelAndView modelAndView, @PathVariable("from") String fromCurrency,
            @PathVariable("to") String toCurrency, @PathVariable("date") String date, Principal principal) {
        ExchangeRateModel exchangeRateModel = currencyExchangeService.getExchangeRate(fromCurrency, toCurrency, date);
        modelAndView.addObject("searchedExchangeRate", exchangeRateModel);
        exchangeRateSearchService.saveExchangeRateSearch(exchangeRateModel, principal.getName());
        modelAndView.setViewName(SEARCH + ":: resultsList");
        return modelAndView;
    }
}
