package com.rg.webmvctest.domain;

public class RegistrationNotFoundException extends RuntimeException {
    public RegistrationNotFoundException(String registrationId) {
        super("No registration with ID: "+ registrationId +" exists.");
    }
}
