package com.rg.webmvctest.adapters.memoryrepository

import com.rg.webmvctest.domain.DuplicateRegistrationException
import com.rg.webmvctest.domain.RegistrationNotFound
import com.rg.webmvctest.domain.UserRegistration
import com.rg.webmvctest.domain.UserRegistrationRepository
import org.springframework.stereotype.Component
import org.springframework.util.DigestUtils
import java.security.SecureRandom
import java.util.concurrent.ConcurrentHashMap

@Component
open class InMemoryUserRegistrationRepository : UserRegistrationRepository {

    private val secureRandom = SecureRandom()
    private val storage = ConcurrentHashMap<String, UserRegistration>()

    override fun findById(registrationId: String): UserRegistration? =
            storage[registrationId]

    override fun getById(registrationId: String): UserRegistration =
            storage[registrationId] ?: throw RegistrationNotFound(registrationId)

    override fun insert(emailAddress: String, name: String, lastName: String): UserRegistration {
        if (findByEmailAddress(emailAddress) != null) {
            throw DuplicateRegistrationException(emailAddress)
        }

        val registration = UserRegistration(
                registrationId = generateId(emailAddress),
                emailAddress = emailAddress,
                name = name,
                lastName = lastName
        )
        storage[registration.registrationId] = registration

        return registration
    }

    override fun update(userRegistration: UserRegistration): UserRegistration {
        if (storage[userRegistration.registrationId] == null) {
            throw RegistrationNotFound(userRegistration.registrationId)
        }

        storage[userRegistration.registrationId] = userRegistration
        return userRegistration
    }

    override fun findByEmailAddress(emailAddress: String): UserRegistration? =
            storage.values.firstOrNull { it.emailAddress == emailAddress }

    override fun findAll(): List<UserRegistration> = storage.values.toList()

    /**
     * This is an UNSAFE method to produce any sort of ID since MD5 is unsafe.
     * It is used here only to create short text ids.
     */
    private fun generateId(emailAddress: String): String =
         DigestUtils.md5DigestAsHex("${secureRandom.nextInt()}$emailAddress".toByteArray(Charsets.UTF_8)).take(8)
}