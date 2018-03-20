package com.rg.webmvctest.domain

interface UserRegistrationRepository {

    fun findById(registrationId: String): UserRegistration?

    fun getById(registrationId: String): UserRegistration

    fun insert(emailAddress: String, name: String, lastName: String): UserRegistration

    fun update(userRegistration: UserRegistration): UserRegistration

    fun findByEmailAddress(emailAddress: String): UserRegistration?

    fun findAll(): List<UserRegistration>
}

class RegistrationNotFound(registrationId: String)
    : RuntimeException("No registration with ID: $registrationId exists.")

class DuplicateRegistrationException(emailAddress: String)
    : RuntimeException("There already exists a registration with email address: $emailAddress")