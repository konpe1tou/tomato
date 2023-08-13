package com.example.tomato.core.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
public class ErrorResponse {
    @Schema(description = "エラーコード", example = "A0000", required = true)
    String errorCode;

    @Schema(description = "エラーメッセージ", example = "The request is invalid.", required = true)
    String errorMessage;
}
