package com.rg.webmvctest.domain

import org.springframework.stereotype.Component

@Component
open class RegistrationService(
        private val userRegistrationRepository: UserRegistrationRepository
) {

    open fun registerUser(emailAddress: String, name: String, lastName: String): UserRegistration =
            userRegistrationRepository.insert(emailAddress, name, lastName)

    open fun updateRegistration(userRegistration: UserRegistration): UserRegistration =
            userRegistrationRepository.update(userRegistration)

    open fun findUserRegistrationById(registrationId: String): UserRegistration? =
            userRegistrationRepository.findByEmailAddress(registrationId)

    open fun findAllUserRegistrations(): List<UserRegistration> =
            userRegistrationRepository.findAll()
}