package com.example.tomato.core.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorResponseException extends RuntimeException {
    private String code;

    private String message;

    private String level;

    public ErrorResponseException(String code, String message, String level) {
        setCode(code);
        setMessage(message);
        setLevel(level);
    }
}
