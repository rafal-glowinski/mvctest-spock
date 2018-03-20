package com.rg.webmvctest.rest

import com.rg.webmvctest.BaseWebMvcSpec
import com.rg.webmvctest.domain.RegistrationService
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import spock.lang.Unroll
import spock.mock.DetachedMockFactory

import static groovy.json.JsonOutput.toJson
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [UserRegistrationController])
class UserRegistrationValidationSpec extends BaseWebMvcSpec {

    @Unroll
    def "should not allow to create a registration with an invalid email address: #emailAddress"() {
        given:
        Map request = [
                email_address : emailAddress,
                name          : 'John',
                last_name     : 'Wayne'
        ]

        when:
        def results = doRequest(
                post("/registrations").contentType(APPLICATION_JSON).content(toJson(request))
        )

        then:
        results.andExpect(status().isUnprocessableEntity())

        and:
        results.andExpect(jsonPath("\$.errors[0].code").value("MethodArgumentNotValidException"))
        results.andExpect(jsonPath("\$.errors[0].path").value("emailAddress"))
        results.andExpect(jsonPath("\$.errors[0].userMessage").value(userMessage))

        where:
        emailAddress              || userMessage
        "john.wayne(at)gmail.com" || "Invalid email address."
        "abcdefg"                 || "Invalid email address."
        ""                        || "Invalid email address."
        null                      || "Email must be provided."
    }

    @Unroll
    def "should not allow to create a registration with an invalid name: #name"() {
        given:
        Map request = [
                email_address : 'john.wayne@gmail.com',
                name          : name,
                last_name     : 'Wayne'
        ]

        when:
        def results = doRequest(
                post("/registrations").contentType(APPLICATION_JSON).content(toJson(request))
        )

        then:
        results.andExpect(status().isUnprocessableEntity())

        and:
        results.andExpect(jsonPath("\$.errors[0].code").value("MethodArgumentNotValidException"))
        results.andExpect(jsonPath("\$.errors[0].path").value("name"))
        results.andExpect(jsonPath("\$.errors[0].userMessage").value(userMessage))

        where:
        name      || userMessage
        null      || "Name must be provided."
        "I"       || "Name must be at least 2 characters and at most 50 characters long."
        ""        || "Name must be at least 2 characters and at most 50 characters long."
    }

    @TestConfiguration
    static class StubConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        RegistrationService registrationService() {
            return detachedMockFactory.Stub(RegistrationService)
        }
    }
}