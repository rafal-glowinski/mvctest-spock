package com.rg.webmvctest.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExistingUserRegistrationDTO {

    private final String registrationId;
    private final String emailAddress;
    private final String name;
    private final String lastName;

    public ExistingUserRegistrationDTO(String registrationId, String emailAddress, String name, String lastName) {
        this.registrationId = registrationId;
        this.emailAddress = emailAddress;
        this.name = name;
        this.lastName = lastName;
    }

    @JsonProperty("registration_id")
    public String getRegistrationId() {
        return registrationId;
    }

    @JsonProperty("email_address")
    public String getEmailAddress() {
        return emailAddress;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("last_name")
    public String getLastName() {
        return lastName;
    }
}
