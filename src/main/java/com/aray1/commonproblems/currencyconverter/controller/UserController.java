package com.aray1.commonproblems.currencyconverter.controller;

import static com.aray1.commonproblems.currencyconverter.ApplicationConstants.LOGIN;
import static com.aray1.commonproblems.currencyconverter.ApplicationConstants.REGISTER;
import static com.aray1.commonproblems.currencyconverter.ApplicationConstants.USER;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.aray1.commonproblems.currencyconverter.entity.UserEntity;
import com.aray1.commonproblems.currencyconverter.model.UserModel;
import com.aray1.commonproblems.currencyconverter.repository.UserRepository;
import com.aray1.commonproblems.currencyconverter.service.mapper.UserMapper;

/**
 * Controller to handle user login and registration.
 */
@Controller
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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

    private void populateCountries(ModelAndView modelAndView) {
        modelAndView.addObject("countries",
                Arrays.stream(Locale.getISOCountries()).map(s -> new Locale("", s)).map(Locale::getDisplayCountry)
                        .collect(Collectors.toList()));
    }

}
