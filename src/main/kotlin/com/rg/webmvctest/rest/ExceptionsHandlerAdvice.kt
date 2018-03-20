package com.rg.webmvctest.rest

import com.rg.webmvctest.rest.util.ErrorsHolder
import com.rg.webmvctest.rest.util.ExceptionMapperHelper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
open class ExceptionsHandlerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    open fun handleException(exception: MethodArgumentNotValidException): ResponseEntity<ErrorsHolder> {
        val errors = ErrorsHolder(ExceptionMapperHelper.errorsFromBindResult(exception, exception.bindingResult))

        return ExceptionMapperHelper.mapResponseWithoutLogging(errors, HttpStatus.UNPROCESSABLE_ENTITY)
    }
}