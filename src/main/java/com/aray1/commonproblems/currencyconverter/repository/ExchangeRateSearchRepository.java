package com.aray1.commonproblems.currencyconverter.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.aray1.commonproblems.currencyconverter.entity.ExchangeRateSearchEntity;

/**
 * Repository to handle CRUD operations for {@link ExchangeRateSearchEntity}.
 */
public interface ExchangeRateSearchRepository extends JpaRepository<ExchangeRateSearchEntity, Long> {

    List<ExchangeRateSearchEntity> findByUserEntity(@Param("email") String userEmail, Pageable pageable);
}
