package com.example.tomato.external.entity;

import com.example.tomato.core.constants.OrderStatus;
import com.example.tomato.external.dto.request.order.OrderRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonOrder {
    @Id
    @Schema(description = "ID", example = "0", required = true)
    private Long id;

    @Schema(description = "商品ID", example = "牛乳", required = true)
    private Long productId;

    @Schema(description = "数量", example = "4")
    private Long quantity;

    @Schema(description = "注文ステータス", example = "CREATED")
    private OrderStatus status;

    @Schema(description = "注文日時", example = "2023-01-01T00:00:00.000")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime orderDate;

    public CommonOrder(OrderRequest request, Long id, LocalDateTime orderDate) {
        this.id = id;
        this.productId = request.getProductId();
        this.quantity = request.getQuantity();
        this.status = OrderStatus.CREATED;
        this.orderDate = orderDate;
    };
}
