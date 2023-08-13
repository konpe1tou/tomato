package com.example.tomato.external.dto.request.refrigerator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class RefrigeratorRequest {

    @NotNull
    @Schema(description = "名称", example = "牛乳", required = true)
    protected String name;

    @Schema(description = "数量", example = "4")
    protected Long quantity;

    @Schema(description = "賞味期限/消費期限", example = "2023-01-01T00:00:00.000")
    protected LocalDateTime expiry;
}
