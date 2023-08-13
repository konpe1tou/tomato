package com.example.tomato.core.error;

import com.example.tomato.core.exception.NotFoundIdException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Hidden
public class ErrorHandler {
    @ExceptionHandler({NotFoundIdException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundIdException(NotFoundIdException e) {
        return new ErrorResponse(e.getCode(), e.getMessage());
    }
}
