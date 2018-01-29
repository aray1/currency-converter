package com.aray1.commonproblems.currencyconverter.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.aray1.commonproblems.currencyconverter.entity.UserEntity;
import com.aray1.commonproblems.currencyconverter.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ApplicationUserDetailsService applicationUserDetailsService;

    @Test
    public void loadUserByUsername() {
        //given
        String userEmail = "a.r@p.net";
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("Whatever123$");
        when(userRepository.findByEmail(eq(userEmail))).thenReturn(userEntity);

        //when
        UserDetails userDetails = applicationUserDetailsService.loadUserByUsername(userEmail);

        //then
        assertEquals("Incorrect userName", userEmail, userDetails.getUsername());
        assertEquals("Incorrect password", userEntity.getPassword(), userDetails.getPassword());
        assertEquals("Incorrect authority", new SimpleGrantedAuthority("USER"),
                userDetails.getAuthorities().iterator().next());
    }

}