package com.aray1.commonproblems.currencyconverter.controller;

import static com.aray1.commonproblems.currencyconverter.ApplicationConstants.LOGIN;
import static com.aray1.commonproblems.currencyconverter.ApplicationConstants.REGISTER;
import static com.aray1.commonproblems.currencyconverter.ApplicationConstants.SEARCH;
import static com.aray1.commonproblems.currencyconverter.ApplicationConstants.USER;

import java.security.Principal;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.aray1.commonproblems.currencyconverter.entity.UserEntity;
import com.aray1.commonproblems.currencyconverter.model.ExchangeRateModel;
import com.aray1.commonproblems.currencyconverter.model.UserModel;
import com.aray1.commonproblems.currencyconverter.repository.UserRepository;
import com.aray1.commonproblems.currencyconverter.service.CurrencyExchangeService;
import com.aray1.commonproblems.currencyconverter.service.ExchangeRateSearchService;
import com.aray1.commonproblems.currencyconverter.service.UserMapper;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CurrencyExchangeService currencyExchangeService;
    private final ExchangeRateSearchService exchangeRateSearchService;

    @Autowired
    public UserController(UserRepository userRepository, UserMapper userMapper,
            CurrencyExchangeService currencyExchangeService, ExchangeRateSearchService exchangeRateSearchService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.currencyExchangeService = currencyExchangeService;
        this.exchangeRateSearchService = exchangeRateSearchService;
    }

    @GetMapping("/")
    public RedirectView redirectWithUsingRedirectView(RedirectAttributes attributes) {
        return new RedirectView("/search");
    }

    @RequestMapping(value = "/" + LOGIN, method = RequestMethod.GET)
    public ModelAndView showLoginPage(ModelAndView modelAndView, UserModel userModel) {
        modelAndView.addObject(USER, userModel);
        modelAndView.setViewName(LOGIN);
        return modelAndView;
    }

    @RequestMapping(value = "/" + REGISTER, method = RequestMethod.GET)
    public ModelAndView showRegistrationPage(ModelAndView modelAndView, UserModel user) {
        populateCountries(modelAndView);
        modelAndView.addObject(USER, user);
        modelAndView.setViewName(REGISTER);
        return modelAndView;
    }

    @RequestMapping(value = "/" + REGISTER, method = RequestMethod.POST)
    public ModelAndView processRegistrationForm(ModelAndView modelAndView,
            @ModelAttribute("user") @Valid UserModel user, BindingResult bindingResult, HttpServletRequest request) {
        populateCountries(modelAndView);
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(REGISTER);
            return modelAndView;
        }

        UserEntity userForGivenEmailAddress = userRepository.findByEmail(user.getEmail());

        if (userForGivenEmailAddress != null) {
            modelAndView.addObject("alreadyRegisteredMessage", "User with provided email already exists");
            modelAndView.setViewName(REGISTER);
            bindingResult.reject("email");
        } else {
            userRepository.save(userMapper.apply(user));
            modelAndView.addObject(USER, new UserModel());
            modelAndView.setViewName(LOGIN);

        }
        return modelAndView;
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

    private void populateCountries(ModelAndView modelAndView) {
        modelAndView.addObject("countries",
                Arrays.stream(Locale.getISOCountries()).map(s -> new Locale("", s)).map(Locale::getDisplayCountry)
                        .collect(Collectors.toList()));
    }

}
