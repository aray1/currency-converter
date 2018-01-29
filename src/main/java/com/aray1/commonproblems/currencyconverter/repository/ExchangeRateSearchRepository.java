package com.aray1.commonproblems.currencyconverter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aray1.commonproblems.currencyconverter.entity.ExchangeRateSearchEntity;

public interface ExchangeRateSearchRepository extends JpaRepository<ExchangeRateSearchEntity, Long> {

    List<ExchangeRateSearchEntity> findByUser(String userEmail);
}
