package com.dhunters.kpop.controller;

import com.dhunters.kpop.core.api.ApiResult;
import com.dhunters.kpop.common.entity.ProductOption;
import com.dhunters.kpop.dto.product.ProductOptionCreateRequest;
import com.dhunters.kpop.dto.product.ProductOptionResponse;
import com.dhunters.kpop.service.ProductOptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/products/{productId}/options")
@RequiredArgsConstructor
public class ProductOptionController {

    private final ProductOptionService productOptionService;

    @GetMapping
    public ResponseEntity<ApiResult<List<ProductOptionResponse>>> getProductOptions(@PathVariable Long productId) {
        List<ProductOption> options = productOptionService.findActiveOptionsByProductId(productId);
        List<ProductOptionResponse> response = options.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResult.success(response));
    }

    @PostMapping
    public ResponseEntity<ApiResult<ProductOptionResponse>> createOption(
            @PathVariable Long productId,
            @Valid @RequestBody ProductOptionCreateRequest request) {

        ProductOption option = convertToEntity(request);
        ProductOption savedOption = productOptionService.createOption(productId, option);
        ProductOptionResponse response = convertToResponse(savedOption);

        return ResponseEntity.ok(ApiResult.success(response));
    }

    @PutMapping("/{optionId}")
    public ResponseEntity<ApiResult<ProductOptionResponse>> updateOption(
            @PathVariable Long productId,
            @PathVariable Long optionId,
            @Valid @RequestBody ProductOptionCreateRequest request) {

        ProductOption updateInfo = convertToEntity(request);
        ProductOption updatedOption = productOptionService.updateOption(productId, optionId, updateInfo);
        ProductOptionResponse response = convertToResponse(updatedOption);

        return ResponseEntity.ok(ApiResult.success(response));
    }

    @DeleteMapping("/{optionId}")
    public ResponseEntity<ApiResult<Void>> deleteOption(
            @PathVariable Long productId,
            @PathVariable Long optionId) {

        productOptionService.deleteOption(productId, optionId);
        return ResponseEntity.ok(ApiResult.success(null));
    }

    @PatchMapping("/{optionId}/stock")
    public ResponseEntity<ApiResult<Void>> updateStock(
            @PathVariable Long productId,
            @PathVariable Long optionId,
            @RequestParam Integer quantity) {

        productOptionService.updateStock(optionId, quantity);
        return ResponseEntity.ok(ApiResult.success(null));
    }

    @GetMapping("/{optionId}/stock-check")
    public ResponseEntity<ApiResult<Boolean>> checkStock(
            @PathVariable Long productId,
            @PathVariable Long optionId,
            @RequestParam Integer quantity) {

        boolean inStock = productOptionService.isInStock(optionId, quantity);
        return ResponseEntity.ok(ApiResult.success(inStock));
    }

    private ProductOption convertToEntity(ProductOptionCreateRequest request) {
        ProductOption option = new ProductOption();
        option.setOptionValue(request.getOptionValue());
        option.setOptionValueEn(request.getOptionValueEn());
        option.setPriceAdjustment(request.getPriceAdjustment());
        option.setSku(request.getSku());
        option.setStockQty(request.getStockQty());
        option.setWeightGram(request.getWeightGram());
        option.setColorHex(request.getColorHex());
        option.setSortOrder(request.getSortOrder());
        option.setIsActive(request.getIsActive());

        return option;
    }

    private ProductOptionResponse convertToResponse(ProductOption option) {
        ProductOptionResponse response = new ProductOptionResponse();
        response.setOptionId(option.getOptionId());
        response.setOptionValue(option.getOptionValue());
        response.setOptionValueEn(option.getOptionValueEn());
        response.setPriceAdjustment(option.getPriceAdjustment());
        response.setSku(option.getSku());
        response.setStockQty(option.getStockQty());
        response.setWeightGram(option.getWeightGram());
        response.setColorHex(option.getColorHex());
        response.setSortOrder(option.getSortOrder());
        response.setIsActive(option.getIsActive());

        return response;
    }
}

