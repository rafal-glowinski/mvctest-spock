package com.rg.webmvctest.rest.util;

import java.util.Arrays;
import java.util.List;

public class ErrorsHolder {

    private final List<Error> errors;

    public ErrorsHolder(List<Error> errors) {
        this.errors = errors;
    }

    public ErrorsHolder(Error... errors) {
        this.errors = Arrays.asList(errors);
    }

    public List<Error> getErrors() {
        return errors;
    }
}