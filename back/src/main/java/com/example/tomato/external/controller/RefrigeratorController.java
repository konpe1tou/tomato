package com.example.tomato.external.controller;

import com.example.tomato.core.annotation.OutputApiLog;
import com.example.tomato.core.error.ErrorResponse;
import com.example.tomato.external.dto.request.refrigerator.RefrigeratorCreationRequest;
import com.example.tomato.external.dto.request.refrigerator.RefrigeratorUpdateRequest;
import com.example.tomato.external.entity.CommonRefrigerator;
import com.example.tomato.external.service.RefrigeratorService;
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
@Tag(name = "冷蔵庫管理", description = "冷蔵庫内の在庫管理")
@CrossOrigin(origins = {"*"})
public class RefrigeratorController {

    @Autowired
    RefrigeratorService refrigeratorService;

    @PostMapping("/refrigerator")
    @Operation(summary = "冷蔵庫内の在庫を新たに追加する")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Validated RefrigeratorCreationRequest request) {
        refrigeratorService.create(request);
    }

    @GetMapping("/refrigerator")
    @Operation(summary = "冷蔵庫内の在庫一覧を取得する")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommonRefrigerator.class))))
    public ResponseEntity<Object> read(
            @Parameter(description = "賞味期限/消費期限切れの在庫のみに絞り込む", example = "true") @RequestParam(defaultValue = "false") boolean expired,
            @Parameter(description = "賞味期限/消費期限切れまであとx日の在庫に絞り込む\n\nexpiredがtrueの場合は無効", example = "3") @RequestParam(defaultValue = "") Long expiredBeforeDays
    ) {
        List<CommonRefrigerator> commonRefrigeratorList = refrigeratorService.read(expired, expiredBeforeDays);
        return new ResponseEntity<>(commonRefrigeratorList, HttpStatus.OK);
    }

    @PutMapping("/refrigerator")
    @Operation(summary = "冷蔵庫内の在庫を更新する")
    @ApiResponse(responseCode = "204", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Validated RefrigeratorUpdateRequest request) {
        refrigeratorService.update(request);
    }

    @DeleteMapping("/refrigerator")
    @Operation(summary = "冷蔵庫内の在庫を削除する")
    @ApiResponse(responseCode = "204", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(required = true, description = "削除対象のID", example = "0") @RequestParam Long id) {
        refrigeratorService.delete(id);
    }

    @DeleteMapping("/refrigerator/all")
    @Operation(summary = "すべての冷蔵庫内の在庫を削除する")
    @ApiResponse(responseCode = "204", content = @Content(mediaType = "application/json"))
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll() {
        refrigeratorService.deleteAll();
    }

    @DeleteMapping("/refrigerator/oos")
    @Operation(summary = "数量が0である冷蔵庫内の在庫をすべて削除する")
    @ApiResponse(responseCode = "204", content = @Content(mediaType = "application/json"))
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOos() {
        refrigeratorService.deleteOos();
    }

    @DeleteMapping("/refrigerator/expired")
    @Operation(summary = "賞味期限/消費期限が切れた冷蔵庫内の在庫をすべて削除する")
    @ApiResponse(responseCode = "204", content = @Content(mediaType = "application/json"))
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExpired() {
        refrigeratorService.deleteExpired();
    }
}
