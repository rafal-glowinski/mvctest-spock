package com.rg.webmvctest.rest

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

const val EMAIL_REGEXP = "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"

data class NewUserRegistrationDTO(
        @get:Pattern(regexp = EMAIL_REGEXP, message = "Invalid email address.")
        @get:NotNull(message = "Email must be provided.")
        @JsonProperty("email_address")
        val emailAddress: String?,

        @get:NotNull(message = "Name must be provided.")
        @get:Size(min = 2, max = 50, message = "Name must be at least 2 characters and at most 50 characters long.")
        @JsonProperty("name")
        val name: String?,

        @get:NotNull(message = "Last name must be provided.")
        @get:Size(min = 2, max = 50, message = "Last name must be at least 2 characters and at most 50 characters long.")
        @JsonProperty("last_name")
        val lastName: String?
)

data class ExistingUserRegistrationDTO(
        @JsonProperty("registration_id")
        val registrationId: String,

        @JsonProperty("email_address")
        val emailAddress: String,

        @JsonProperty("name")
        val name: String,

        @JsonProperty("last_name")
        val lastName: String
)