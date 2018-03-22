package com.rg.webmvctest.domain;

import java.util.List;

public interface UserRegistrationRepository {

    UserRegistration findById(String registrationId);

    UserRegistration getById(String registrationId);

    UserRegistration insert(String emailAddress, String name, String lastName);

    UserRegistration update(UserRegistration userRegistration);

    UserRegistration findByEmailAddress(String emailAddress);

    List<UserRegistration> findAll();
}