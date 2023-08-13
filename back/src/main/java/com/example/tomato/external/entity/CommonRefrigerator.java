package com.example.tomato.external.entity;

import com.example.tomato.external.dto.request.refrigerator.RefrigeratorCreationRequest;
import com.example.tomato.external.dto.request.refrigerator.RefrigeratorUpdateRequest;
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
public class CommonRefrigerator {

    @Id
    @Schema(description = "ID", example = "0", required = true)
    private Long id;

    @Schema(description = "名称", example = "牛乳", required = true)
    private String name;

    @Schema(description = "数量", example = "4")
    private Long quantity;

    @Schema(description = "賞味期限/消費期限", example = "2023-01-01T00:00:00.000")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime expiry;

    public CommonRefrigerator(RefrigeratorCreationRequest request, Long id) {
        this.id = id;
        this.name = request.getName();
        this.quantity = request.getQuantity();
        this.expiry = request.getExpiry();
    }

    public CommonRefrigerator(RefrigeratorUpdateRequest request) {
        this.id = request.getId();
        this.name = request.getName();
        this.quantity = request.getQuantity();
        this.expiry = request.getExpiry();
    }
}
