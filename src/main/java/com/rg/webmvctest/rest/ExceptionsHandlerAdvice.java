package com.rg.webmvctest.rest;

import com.rg.webmvctest.rest.util.ErrorsHolder;
import com.rg.webmvctest.rest.util.ExceptionMapperHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandlerAdvice {

    private final ExceptionMapperHelper mapperHelper = new ExceptionMapperHelper();

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorsHolder> handleException(MethodArgumentNotValidException exception) {
        ErrorsHolder errors = new ErrorsHolder(mapperHelper.errorsFromBindResult(exception, exception.getBindingResult()));
        return mapperHelper.mapResponseWithoutLogging(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
