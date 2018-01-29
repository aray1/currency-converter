package com.aray1.commonproblems.currencyconverter.service.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.aray1.commonproblems.currencyconverter.entity.UserEntity;
import com.aray1.commonproblems.currencyconverter.model.AddressModel;
import com.aray1.commonproblems.currencyconverter.model.UserModel;

public class UserMapperTest {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final UserMapper userMapper = new UserMapper(simpleDateFormat.toPattern());
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Test
    public void mapToUserEntity() {
        //given
        UserModel userModel = createUserModel();

        //when
        UserEntity userEntity = userMapper.apply(userModel);

        //then
        assertEquals("Incorrect email", userModel.getEmail(), userEntity.getEmail());
        assertEquals("Incorrect DOB", userModel.getDateOfBirth(), simpleDateFormat.format(userEntity.getDateOfBirth()));
        assertTrue("Incorrect password",
                bCryptPasswordEncoder.matches(userModel.getPassword(), userEntity.getPassword()));

        AddressModel addressModel = userModel.getAddress();
        assertEquals("Incorrect country", addressModel.getCountry(), userEntity.getAddressEntity().getCountry());
        assertEquals("Incorrect city", addressModel.getCity(), userEntity.getAddressEntity().getCity());
        assertEquals("Incorrect street", addressModel.getStreet(), userEntity.getAddressEntity().getStreet());
        assertEquals("Incorrect postcode", addressModel.getPostCode(), userEntity.getAddressEntity().getPostCode());
    }

    private UserModel createUserModel() {
        UserModel userModel = new UserModel();
        userModel.setEmail("a.r@p.net");
        userModel.setDateOfBirth("1999-01-02");
        userModel.setPassword("Whatever123$");
        userModel.setAddress(createAddressModel());
        return userModel;
    }

    private AddressModel createAddressModel() {
        AddressModel addressModel = new AddressModel();
        addressModel.setCountry("Germany");
        addressModel.setCity("Munich");
        addressModel.setStreet("Sch Str");
        addressModel.setPostCode("80888");
        return addressModel;
    }

}