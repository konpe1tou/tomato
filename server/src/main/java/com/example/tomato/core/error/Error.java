package com.example.tomato.core.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Error {
    A0001("A0001", "The id is not found.", ""),
    ;

    private final String code;
    private final String message;
    private final String level;
}
