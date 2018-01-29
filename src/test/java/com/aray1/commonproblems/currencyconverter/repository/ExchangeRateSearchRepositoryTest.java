package com.aray1.commonproblems.currencyconverter.repository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import com.aray1.commonproblems.currencyconverter.entity.ExchangeRateSearchEntity;
import com.aray1.commonproblems.currencyconverter.entity.UserEntity;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ExchangeRateSearchRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ExchangeRateSearchRepository exchangeRateSearchRepository;

    @Before
    public void setup() {
        mockSecurityContext();

        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("Whatever123$");
        userEntity.setEmail("a.r@p.net");
        userEntity.setDateOfBirth(new Date());
        entityManager.persist(userEntity);

        for (int i = 0; i < 4; i++) {
            ExchangeRateSearchEntity exchangeRateSearchEntity = new ExchangeRateSearchEntity();
            exchangeRateSearchEntity.setFromCurrency("EUR");
            exchangeRateSearchEntity.setToCurrency("USD");
            exchangeRateSearchEntity.setUserEntity(userEntity);
            exchangeRateSearchEntity.setExchangeRate(1.7);
            exchangeRateSearchEntity.setExchangeRateDate(new Date());
            entityManager.persist(exchangeRateSearchEntity);
        }
        entityManager.flush();
    }

    @Test
    public void findByUserEntity() {
        List<ExchangeRateSearchEntity> byUserEntity =
                exchangeRateSearchRepository.findByUserEntity("a.r@p.net", new PageRequest(0, 3));

        assertEquals("Incorrect result size", 3, byUserEntity.size());
    }

    private void mockSecurityContext() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("user");
    }

}