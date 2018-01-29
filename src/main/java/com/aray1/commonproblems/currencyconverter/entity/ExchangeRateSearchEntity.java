package com.aray1.commonproblems.currencyconverter.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

/**
 * Entity to store exchange rate search results for users.
 */
@Entity
@NamedQuery(name = "ExchangeRateSearchEntity.findByUserEntity",
        query = "SELECT er FROM ExchangeRateSearchEntity er WHERE er.userEntity.email  = :email ORDER BY er.creationDate DESC")
public class ExchangeRateSearchEntity extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String fromCurrency;
    @NotNull
    private String toCurrency;
    private Double exchangeRate;
    @NotNull
    private Date exchangeRateDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    public Long getId() {
        return id;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Date getExchangeRateDate() {
        return exchangeRateDate;
    }

    public void setExchangeRateDate(Date exchangeRateDate) {
        this.exchangeRateDate = exchangeRateDate;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
