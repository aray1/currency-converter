package com.aray1.commonproblems.currencyconverter.entity;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * AuditorAware implementation to get the principal.
 */
public class AuditAwareImpl implements AuditorAware<String> {
    @Override
    public String getCurrentAuditor() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
