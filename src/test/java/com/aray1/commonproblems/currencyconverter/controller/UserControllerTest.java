package com.aray1.commonproblems.currencyconverter.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.servlet.ModelAndView;

import com.aray1.commonproblems.currencyconverter.entity.UserEntity;
import com.aray1.commonproblems.currencyconverter.model.UserModel;
import com.aray1.commonproblems.currencyconverter.repository.UserRepository;
import com.aray1.commonproblems.currencyconverter.service.mapper.UserMapper;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    @Test
    public void showLoginPage() {
        ModelAndView actualModelAndView = userController.showLoginPage(new ModelAndView(), new UserModel());

        assertEquals("Incorrect view name", "login", actualModelAndView.getViewName());
        assertNotNull("User model not set", actualModelAndView.getModel().get("user"));
    }

    @Test
    public void showRegistrationPage() {
        ModelAndView actualModelAndView = userController.showRegistrationPage(new ModelAndView(), new UserModel());

        assertEquals("Incorrect view name", "register", actualModelAndView.getViewName());
        assertNotNull("User model not set", actualModelAndView.getModel().get("user"));
        assertNotNull("Countries not set", actualModelAndView.getModel().get("countries"));
    }

    @Test
    public void processRegistrationFormHasErrors() {
        BindingResult bindingResult = new MapBindingResult(new HashMap<>(), "");
        bindingResult.addError(new FieldError("user", "user.email", "incorrectEmail"));
        ModelAndView actualModelAndView =
                userController.processRegistrationForm(new ModelAndView(), new UserModel(), bindingResult, null);

        assertEquals("Incorrect view name", "register", actualModelAndView.getViewName());
        verify(userRepository, times(0)).findByEmail(anyString());
    }

    @Test
    public void processRegistrationFormDuplicateEmailAddress() {
        BindingResult bindingResult = new MapBindingResult(new HashMap<>(), "");
        when(userRepository.findByEmail(anyString())).thenReturn(new UserEntity());

        ModelAndView actualModelAndView =
                userController.processRegistrationForm(new ModelAndView(), new UserModel(), bindingResult, null);

        assertEquals("Incorrect view name", "register", actualModelAndView.getViewName());
        assertEquals("Incorrect error count", 1, bindingResult.getErrorCount());
    }

    @Test
    public void processRegistrationFormSuccess() {
        BindingResult bindingResult = new MapBindingResult(new HashMap<>(), "");
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        ModelAndView actualModelAndView =
                userController.processRegistrationForm(new ModelAndView(), new UserModel(), bindingResult, null);

        assertEquals("Incorrect view name", "login", actualModelAndView.getViewName());
        assertNotNull("User model not set", actualModelAndView.getModel().get("user"));
        assertNotNull("Countries not set", actualModelAndView.getModel().get("countries"));
    }

}