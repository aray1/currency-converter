package com.aray1.commonproblems.currencyconverter.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aray1.commonproblems.currencyconverter.entity.ExchangeRateSearchEntity;
import com.aray1.commonproblems.currencyconverter.entity.UserEntity;
import com.aray1.commonproblems.currencyconverter.model.ExchangeRateModel;
import com.aray1.commonproblems.currencyconverter.repository.ExchangeRateSearchRepository;
import com.aray1.commonproblems.currencyconverter.repository.UserRepository;
import com.aray1.commonproblems.currencyconverter.service.mapper.ExchangeRateSearchEntityMapper;
import com.aray1.commonproblems.currencyconverter.service.mapper.ExchangeRateSearchMapper;

@Service
public class ExchangeRateSearchService {

    private final ExchangeRateSearchRepository exchangeRateSearchRepository;
    private final UserRepository userRepository;
    private final ExchangeRateSearchEntityMapper exchangeRateSearchEntityMapper;

    @Autowired
    public ExchangeRateSearchService(ExchangeRateSearchRepository exchangeRateSearchRepository,
            UserRepository userRepository, ExchangeRateSearchEntityMapper exchangeRateSearchEntityMapper) {
        this.exchangeRateSearchRepository = exchangeRateSearchRepository;
        this.userRepository = userRepository;
        this.exchangeRateSearchEntityMapper = exchangeRateSearchEntityMapper;
    }

    public List<ExchangeRateModel> getExchangeRateSearchesForUser(String userEmail) {
        return exchangeRateSearchRepository.findByUser(userEmail).stream().map(new ExchangeRateSearchMapper())
                .collect(Collectors.toList());
    }

    public void saveExchangeRateSearch(ExchangeRateModel exchangeRateModel, String userEmail) {
        UserEntity userEntity = userRepository.findByEmail(userEmail);
        ExchangeRateSearchEntity exchangeRateSearchEntity = exchangeRateSearchEntityMapper.apply(exchangeRateModel);
        exchangeRateSearchEntity.setUserEntity(userEntity);
        exchangeRateSearchRepository.save(exchangeRateSearchEntity);
    }

}
