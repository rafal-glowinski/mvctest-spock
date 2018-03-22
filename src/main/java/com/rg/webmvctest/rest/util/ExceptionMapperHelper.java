package com.rg.webmvctest.rest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

public class ExceptionMapperHelper {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionMapperHelper.class);

    private static final String INVALID_VALUE_DETAILS_PREFIX = "Invalid value: ";
    private static final String OBJECT_ERROR_REJECTED_VALUE = "unknown";

    public <T extends Exception> ResponseEntity<ErrorsHolder> mapResponse(T exception, HttpStatus httpStatus)
    {
        logger.error(
                "Got exception from Spring Web, mapping to HTTP status={}",
                httpStatus, exception
        );
        return mapResponseWithoutLogging(exception, httpStatus);
    }

    public List<Error> errorsFromBindResult(Exception exception, BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
                .map(objectError ->
                    Error.error(exception.getClass().getSimpleName())
                            .withMessage(INVALID_VALUE_DETAILS_PREFIX + getRejectedValue(objectError))
                            .withUserMessage(objectError.getDefaultMessage())
                            .withPath(getPath(objectError))
                            .withDetails("")
                            .build()
                ).collect(Collectors.toList());
    }

    private String getPath(ObjectError objectError) {
        return (objectError instanceof FieldError) ? ((FieldError) objectError).getField() : objectError.getObjectName();
    }

    private Object getRejectedValue(ObjectError objectError) {
        return  (objectError instanceof FieldError) ? ((FieldError) objectError).getRejectedValue() : OBJECT_ERROR_REJECTED_VALUE;
    }

    public <T extends Exception> ResponseEntity<ErrorsHolder> mapResponseWithoutLogging(T exception, HttpStatus httpStatus) {
        return mapResponseWithoutLogging(new ErrorsHolder(Error.error().fromException(exception).build()), httpStatus);
    }

    public ResponseEntity<ErrorsHolder> mapResponseWithoutLogging(ErrorsHolder errors, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).contentType(MediaType.APPLICATION_JSON).body(errors);
    }
}
