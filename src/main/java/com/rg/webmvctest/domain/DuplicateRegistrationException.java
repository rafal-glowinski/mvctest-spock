package com.rg.webmvctest.domain;

public class DuplicateRegistrationException extends RuntimeException {

    public DuplicateRegistrationException(String emailAddress) {
        super("There already exists a registration with email address: " + emailAddress);
    }
}
