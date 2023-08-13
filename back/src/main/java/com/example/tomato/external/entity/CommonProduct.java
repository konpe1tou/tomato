package com.example.tomato.external.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonProduct {
    @Id
    @Schema(description = "ID", example = "0", required = true)
    private Long id;

    @Schema(description = "名称", example = "牛乳", required = true)
    private String name;

    @Schema(description = "価格目安", example = "200", required = true)
    private Long price;

    @Schema(description = "セール時価格目安", example = "200", required = true)
    private Long salePrice;

    @Schema(description = "セール中フラグ", example = "false", required = true)
    private boolean isSale;
}
