package com.aray1.commonproblems.currencyconverter.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.function.Function;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.aray1.commonproblems.currencyconverter.entity.AddressEntity;
import com.aray1.commonproblems.currencyconverter.entity.UserEntity;
import com.aray1.commonproblems.currencyconverter.model.AddressModel;
import com.aray1.commonproblems.currencyconverter.model.UserModel;

/**
 * Class to map an {@link com.aray1.commonproblems.currencyconverter.model.UserModel} to an User
 */
@Service
public class UserMapper implements Function<UserModel, UserEntity> {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserEntity apply(UserModel userModel) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userModel.getEmail());
        try {
            userEntity.setDateOfBirth(new SimpleDateFormat("YYYY-MM-dd").parse(userModel.getDateOfBirth()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        userEntity.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));

        AddressEntity addressEntity = new AddressEntity();
        AddressModel addressModel = userModel.getAddress();
        addressEntity.setCountry(addressModel.getCountry());
        addressEntity.setCity(addressModel.getCity());
        addressEntity.setStreet(addressModel.getStreet());
        addressEntity.setPostCode(addressModel.getPostCode());

        userEntity.setAddressEntity(addressEntity);

        return userEntity;
    }
}
