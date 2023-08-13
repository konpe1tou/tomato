package com.example.tomato.external.dto.request.refrigerator;

import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class RefrigeratorCreationRequest extends RefrigeratorRequest {

    public RefrigeratorCreationRequest(String name, Long quantity, LocalDateTime expiry) {
        this.name = name;
        this.quantity = quantity;
        this.expiry = expiry;
    }
}
