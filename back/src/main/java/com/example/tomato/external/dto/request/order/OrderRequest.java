package com.example.tomato.external.dto.request.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class OrderRequest {

    @Schema(description = "商品ID", example = "牛乳", required = true)
    private Long productId;

    @Schema(description = "数量", example = "4")
    private Long quantity;
}
