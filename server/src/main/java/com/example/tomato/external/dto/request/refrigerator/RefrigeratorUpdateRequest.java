package com.example.tomato.external.dto.request.refrigerator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class RefrigeratorUpdateRequest extends RefrigeratorRequest {

    @NotNull
    @Schema(description = "更新対象のID", example = "0", required = true)
    private Long id;

    public RefrigeratorUpdateRequest(Long id, String name, long quantity, LocalDateTime expiry) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.expiry = expiry;
    }
}
