package com.aray1.commonproblems.currencyconverter.config;

import static com.aray1.commonproblems.currencyconverter.ApplicationConstants.SEARCH;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Configuration to redirect default context path.
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName(SEARCH);
    }

}