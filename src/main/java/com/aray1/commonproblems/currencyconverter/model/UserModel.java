package com.aray1.commonproblems.currencyconverter.model;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Model class representing the user.
 */
public class UserModel {

    @NotEmpty(message = "Field is mandatory")
    @Email(message = "Not a valid email address")
    private String email;
    @NotEmpty(message = "Field is mandatory")
    private String dateOfBirth;
    @NotEmpty(message = "Field is mandatory")
    private String password;
    @Valid
    private AddressModel address = new AddressModel();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AddressModel getAddress() {
        return address;
    }

    public void setAddress(AddressModel address) {
        this.address = address;
    }

}
