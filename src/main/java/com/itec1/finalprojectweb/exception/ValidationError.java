package com.itec1.finalprojectweb.exception;

import lombok.Getter;

@Getter
public class ValidationError extends Throwable {

    private final String field;
    private final String message;

    public ValidationError(String field, String message) {
        super();
        this.field = field;
        this.message = message;
    }

}
