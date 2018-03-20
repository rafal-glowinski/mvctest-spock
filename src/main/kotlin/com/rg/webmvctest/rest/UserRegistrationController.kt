package com.rg.webmvctest.rest

import com.rg.webmvctest.domain.RegistrationService
import com.rg.webmvctest.domain.UserRegistration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping(path = ["/registrations"])
open class UserRegistrationController(
        private val registrationService: RegistrationService
) {

    @PostMapping(consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    open fun register(@RequestBody @Valid newUserRegistration: NewUserRegistrationDTO): ExistingUserRegistrationDTO =
            registrationService.registerUser(newUserRegistration.emailAddress!!, newUserRegistration.name!!, newUserRegistration.lastName!!).asDTO()

    @PutMapping(path = ["/{registrationId}"], consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    open fun updateRegistration(
            @RequestBody @Valid newUserRegistration: NewUserRegistrationDTO,
            @PathVariable("registrationId") registrationId: String
    ): ExistingUserRegistrationDTO =
            registrationService.updateRegistration(newUserRegistration.asUserRegistration(registrationId)).asDTO()

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    open fun getAllRegistrations(): List<ExistingUserRegistrationDTO> =
            registrationService.findAllUserRegistrations().map { it.asDTO() }

    private fun UserRegistration.asDTO(): ExistingUserRegistrationDTO =
            ExistingUserRegistrationDTO(
                    registrationId = registrationId,
                    emailAddress = emailAddress,
                    name = name,
                    lastName = lastName
            )

    private fun NewUserRegistrationDTO.asUserRegistration(registrationId: String): UserRegistration =
            UserRegistration(
                    registrationId = registrationId,
                    emailAddress = emailAddress!!,
                    name = name!!,
                    lastName = lastName!!
            )
}