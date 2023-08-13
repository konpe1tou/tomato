package com.example.tomato.core.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UniqueIdType {

    REFRIGERATOR(0L),
    ORDER(0L),
    PRODUCT(0L),
    ;
    private final Long initialCount;
}
