package com.rg.webmvctest.adapters.memoryrepository;

import com.rg.webmvctest.domain.DuplicateRegistrationException;
import com.rg.webmvctest.domain.RegistrationNotFoundException;
import com.rg.webmvctest.domain.UserRegistration;
import com.rg.webmvctest.domain.UserRegistrationRepository;
import kotlin.text.Charsets;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryUserRegistrationRepository implements UserRegistrationRepository {

    private final SecureRandom secureRandom = new SecureRandom();
    private final ConcurrentHashMap<String, UserRegistration> storage = new ConcurrentHashMap<>();


    @Override
    public UserRegistration findById(String registrationId) {
        return storage.get(registrationId);
    }

    @Override
    public UserRegistration getById(String registrationId) {
        if (!storage.containsKey(registrationId)) {
            throw new RegistrationNotFoundException(registrationId);
        }

        return storage.get(registrationId);
    }

    @Override
    public UserRegistration insert(String emailAddress, String name, String lastName) {
        if (findByEmailAddress(emailAddress) != null) {
            throw new DuplicateRegistrationException(emailAddress);
        }

        UserRegistration registration = new UserRegistration(
                generateId(emailAddress),
                emailAddress,
                name,
                lastName
        );

        storage.put(registration.getRegistrationId(), registration);
        return registration;
    }

    @Override
    public UserRegistration update(UserRegistration userRegistration) {
        if (!storage.containsKey(userRegistration.getRegistrationId())) {
            throw new RegistrationNotFoundException(userRegistration.getRegistrationId());
        }

        storage.put(userRegistration.getRegistrationId(), userRegistration);
        return userRegistration;
    }

    @Override
    public UserRegistration findByEmailAddress(String emailAddress) {
        return storage.values().stream().filter(it -> it.getEmailAddress().equals(emailAddress)).findFirst().orElse(null);
    }

    @Override
    public List<UserRegistration> findAll() {
        return new ArrayList<>(storage.values());
    }

    /**
     * This is an UNSAFE method to produce any sort of ID since MD5 is unsafe.
     * It is used here only to create short text ids.
     */
    private String generateId(String emailAddress) {
        int salt = secureRandom.nextInt();
        return DigestUtils.md5DigestAsHex(String.format("%d%s", salt, emailAddress).getBytes(Charsets.UTF_8)).substring(0, 8);
    }
}
