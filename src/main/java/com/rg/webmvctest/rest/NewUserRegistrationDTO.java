package com.rg.webmvctest.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.rg.webmvctest.SystemConstants.EMAIL_REGEXP;

public class NewUserRegistrationDTO {

    private final String emailAddress;
    private final String name;
    private final String lastName;

    @JsonCreator
    public NewUserRegistrationDTO(
            @JsonProperty("email_address")
            String emailAddress,

            @JsonProperty("name")
            String name,

            @JsonProperty("last_name")
            String lastName
    ) {
        this.emailAddress = emailAddress;
        this.name = name;
        this.lastName = lastName;
    }

    @Pattern(regexp = EMAIL_REGEXP, message = "Invalid email address.")
    @NotNull(message = "Email must be provided.")
    public String getEmailAddress() {
        return emailAddress;
    }

    @NotNull(message = "Name must be provided.")
    @Size(min = 2, max = 50, message = "Name must be at least 2 characters and at most 50 characters long.")
    public String getName() {
        return name;
    }

    @NotNull(message = "Last name must be provided.")
    @Size(min = 2, max = 50, message = "Last name must be at least 2 characters and at most 50 characters long.")
    public String getLastName() {
        return lastName;
    }
}
