package com.example.tomato.external.controller;

import com.example.tomato.core.annotation.OutputApiLog;
import com.example.tomato.external.dto.request.order.OrderRequest;
import com.example.tomato.external.dto.request.refrigerator.RefrigeratorCreationRequest;
import com.example.tomato.external.entity.CommonOrder;
import com.example.tomato.external.entity.CommonRefrigerator;
import com.example.tomato.external.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@OutputApiLog
@Tag(name = "注文", description = "商品の注文機能")
@CrossOrigin(origins = {"*"})
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/order")
    @Operation(summary = "注文を新たにリクエストする")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Validated OrderRequest request) {
        orderService.create(request);
    }

    @GetMapping("/order")
    @Operation(summary = "注文一覧を取得する")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommonRefrigerator.class))))
    public ResponseEntity<Object> read() {
        List<CommonOrder> commonRefrigeratorList = orderService.read();
        return new ResponseEntity<>(commonRefrigeratorList, HttpStatus.OK);
    }

}
