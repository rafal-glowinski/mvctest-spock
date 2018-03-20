package com.rg.webmvctest.rest.util

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError

object ExceptionMapperHelper {
    private val logger = LoggerFactory.getLogger(ExceptionMapperHelper::class.java)

    private const val INVALID_VALUE_DETAILS_PREFIX = "Invalid value: "
    private const val OBJECT_ERROR_REJECTED_VALUE = "unknown"

    fun <T : Exception> mapResponse(exception: T, httpStatus: HttpStatus): ResponseEntity<ErrorsHolder> {
        logger.error(
                "Got exception from Spring Web, mapping to HTTP status={}",
                httpStatus, exception
        )
        return mapResponseWithoutLogging(exception, httpStatus)
    }

    fun errorsFromBindResult(exception: Exception, bindingResult: BindingResult): List<Error> {
        return bindingResult.allErrors
                .map { objectError ->
                    Error.error(exception.javaClass.simpleName)
                            .withMessage(INVALID_VALUE_DETAILS_PREFIX + getRejectedValue(objectError))
                            .withUserMessage(objectError.defaultMessage)
                            .withPath(getPath(objectError))
                            .withDetails("")
                            .build()
                }
    }

    private fun getPath(objectError: ObjectError): String {
        return if (objectError is FieldError) objectError.field else objectError.objectName
    }

    private fun getRejectedValue(objectError: ObjectError): Any? {
        return if (objectError is FieldError) objectError.rejectedValue else OBJECT_ERROR_REJECTED_VALUE
    }

    fun <T : Exception> mapResponseWithoutLogging(exception: T, httpStatus: HttpStatus): ResponseEntity<ErrorsHolder> =
            mapResponseWithoutLogging(ErrorsHolder(listOf(Error.error().fromException(exception).build())), httpStatus)

    fun mapResponseWithoutLogging(
            errors: ErrorsHolder, httpStatus: HttpStatus
    ): ResponseEntity<ErrorsHolder> =
            ResponseEntity.status(httpStatus).contentType(MediaType.APPLICATION_JSON)
                    .body(errors)
}