package com.aray1.commonproblems.currencyconverter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.aray1.commonproblems.currencyconverter.entity.AuditAwareImpl;

/**
 * Configuration to assist in populating auditable fields.
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaConfig {
    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditAwareImpl();
    }
}
