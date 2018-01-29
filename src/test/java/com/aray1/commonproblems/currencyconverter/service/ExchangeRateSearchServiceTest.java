package com.aray1.commonproblems.currencyconverter.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;

import com.aray1.commonproblems.currencyconverter.entity.ExchangeRateSearchEntity;
import com.aray1.commonproblems.currencyconverter.entity.UserEntity;
import com.aray1.commonproblems.currencyconverter.model.ExchangeRateModel;
import com.aray1.commonproblems.currencyconverter.repository.ExchangeRateSearchRepository;
import com.aray1.commonproblems.currencyconverter.repository.UserRepository;
import com.aray1.commonproblems.currencyconverter.service.mapper.ExchangeRateSearchEntityMapper;
import com.aray1.commonproblems.currencyconverter.service.mapper.ExchangeRateSearchMapper;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeRateSearchServiceTest {

    @Mock
    private ExchangeRateSearchRepository exchangeRateSearchRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ExchangeRateSearchEntityMapper exchangeRateSearchEntityMapper;

    @Mock
    private ExchangeRateSearchMapper exchangeRateSearchMapper;

    @InjectMocks
    private ExchangeRateSearchService exchangeRateSearchService;

    @Test
    public void getExchangeRateSearchesForUser() {
        //given
        String userEmail = "a.r@p.net";
        ExchangeRateSearchEntity exchangeRateSearchEntity = new ExchangeRateSearchEntity();
        exchangeRateSearchEntity.setFromCurrency("EUR");
        when(exchangeRateSearchRepository.findByUserEntity(eq(userEmail), eq(new PageRequest(0, 10))))
                .thenReturn(Collections.singletonList(exchangeRateSearchEntity));

        //when
        List<ExchangeRateModel> exchangeRateSearchesForUser =
                exchangeRateSearchService.getExchangeRateSearchesForUser(userEmail);

        //then
        assertEquals("Incorrect result size", 1, exchangeRateSearchesForUser.size());
    }

    @Test
    public void saveExchangeRateSearch() {
        //given
        String userEmail = "a.r@p.net";
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("Whatever123$");
        when(userRepository.findByEmail(eq(userEmail))).thenReturn(userEntity);
        when(exchangeRateSearchEntityMapper.apply(any(ExchangeRateModel.class)))
                .thenReturn(new ExchangeRateSearchEntity());

        //when
        exchangeRateSearchService.saveExchangeRateSearch(new ExchangeRateModel(), userEmail);

        //then
        verify(exchangeRateSearchRepository).save(any(ExchangeRateSearchEntity.class));
    }

}