package com.aray1.commonproblems.currencyconverter.model;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Model class representing the user address.
 */
public class AddressModel {

    @NotEmpty(message = "Field is mandatory")
    private String street;
    @NotEmpty(message = "Field is mandatory")
    private String postCode;
    @NotEmpty(message = "Field is mandatory")
    private String city;
    @NotEmpty(message = "Field is mandatory")
    private String country;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
