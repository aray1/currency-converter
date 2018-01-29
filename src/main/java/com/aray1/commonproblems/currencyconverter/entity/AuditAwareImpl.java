package com.aray1.commonproblems.currencyconverter.entity;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditAwareImpl implements AuditorAware<String> {
    @Override
    public String getCurrentAuditor() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
