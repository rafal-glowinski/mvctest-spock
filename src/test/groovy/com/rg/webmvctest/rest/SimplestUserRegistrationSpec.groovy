package com.rg.webmvctest.rest

import com.rg.webmvctest.domain.RegistrationService
import com.rg.webmvctest.domain.UserRegistration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static groovy.json.JsonOutput.toJson
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [UserRegistrationController])
class SimplestUserRegistrationSpec extends Specification {

    @Autowired
    protected MockMvc mvc

    @Autowired
    RegistrationService registrationService

    def "should pass user registration details to domain component and return 'created' status"() {
        given:
        Map request = [
                email_address : 'john.wayne@gmail.com',
                name          : 'John',
                last_name     : 'Wayne'
        ]

        and:
        registrationService.registerUser('john.wayne@gmail.com', 'John', 'Wayne') >> new UserRegistration(
                'registration-id-1',
                'john.wayne@gmail.com',
                'John',
                'Wayne'
        )

        when:
        def results = mvc.perform(post("/registrations").contentType(APPLICATION_JSON).content(toJson(request)))

        then:
        results.andExpect(status().isCreated())

        and:
        results.andExpect(jsonPath("\$.registration_id").value("registration-id-1"))
        results.andExpect(jsonPath("\$.email_address").value("john.wayne@gmail.com"))
        results.andExpect(jsonPath("\$.name").value("John"))
        results.andExpect(jsonPath("\$.last_name").value("Wayne"))
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
