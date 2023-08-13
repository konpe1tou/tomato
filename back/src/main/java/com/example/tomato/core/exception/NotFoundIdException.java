package com.example.tomato.core.exception;

import com.example.tomato.core.error.Error;

public class NotFoundIdException extends ErrorResponseException {
    public NotFoundIdException(Error error) {
        super(error.getCode(), error.getMessage(), error.getLevel());
    }
}
