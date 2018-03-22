package com.rg.webmvctest.domain;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegistrationService {

    private final UserRegistrationRepository userRegistrationRepository;

    public RegistrationService(UserRegistrationRepository userRegistrationRepository) {
        this.userRegistrationRepository = userRegistrationRepository;
    }

    public UserRegistration registerUser(String emailAddress, String name, String lastName) {
        return userRegistrationRepository.insert(emailAddress, name, lastName);
    }

    public UserRegistration updateRegistration(UserRegistration userRegistration) {
        return userRegistrationRepository.update(userRegistration);
    }

    public UserRegistration findUserRegistrationById(String registrationId) {
        return userRegistrationRepository.findByEmailAddress(registrationId);
    }

    public List<UserRegistration> findAllUserRegistrations() {
        return userRegistrationRepository.findAll();
    }
}

