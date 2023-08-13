package com.example.tomato.core.constants;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderStatus {
    CREATED(0, "CREATED"),
    ACCEPTED(1, "ACCEPTED"),
    SHIPPED(2, "SHIPPED"),
    COMPLETED(3, "COMPLETED"),
    ;

    private int statusId;

    private String statusName;
}
