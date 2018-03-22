package com.rg.webmvctest.domain;

public class UserRegistration {

    private final String registrationId;
    private final String emailAddress;
    private final String name;
    private final String lastName;

    public UserRegistration(String registrationId, String emailAddress, String name, String lastName) {
        this.registrationId = registrationId;
        this.emailAddress = emailAddress;
        this.name = name;
        this.lastName = lastName;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }
}