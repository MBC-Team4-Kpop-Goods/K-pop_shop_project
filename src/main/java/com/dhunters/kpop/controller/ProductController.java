package com.dhunters.kpop.controller;

import com.dhunters.kpop.core.api.ApiResult;
import com.dhunters.kpop.dto.product.ProductCreateRequest;
import com.dhunters.kpop.common.entity.Product;
import com.dhunters.kpop.service.ProductService;
import com.dhunters.kpop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResult<Page<ProductResponse>>> getProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String minPrice,
            @RequestParam(required = false) String maxPrice,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String status,
            Pageable pageable) {

        Page<Product> products = productService.findProductsWithFilters(
                categoryId, keyword, minPrice, maxPrice, brand, status, pageable);
        Page<ProductResponse> response = products.map(this::convertToResponse);

        return ResponseEntity.ok(ApiResult.success(response));
    }

    @PostMapping
    public ResponseEntity<ApiResult<ProductResponse>> createProduct(@RequestBody ProductCreateRequest request) {
        Product product = convertToEntity(request);
        Product savedProduct = productService.createProduct(product);
        ProductResponse response = convertToResponse(savedProduct);

        return ResponseEntity.ok(ApiResult.success(response));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResult<ProductResponse>> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductCreateRequest request) {

        Product updateInfo = convertToEntity(request);
        Product updatedProduct = productService.updateProduct(productId, updateInfo);
        ProductResponse response = convertToResponse(updatedProduct);

        return ResponseEntity.ok(ApiResult.success(response));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResult<Void>> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok(ApiResult.success(null));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResult<ProductResponse>> getProduct(@PathVariable Long productId) {
        // 조회수 증가
        productService.increaseViewCount(productId);

        Product product = productService.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        ProductResponse response = convertToResponse(product);
        return ResponseEntity.ok(ApiResult.success(response));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResult<Page<ProductResponse>>> getProductsByCategory(
            @PathVariable Long categoryId,
            Pageable pageable) {

        Page<Product> products = productService.findByCategory(categoryId, pageable);
        Page<ProductResponse> response = products.map(this::convertToResponse);

        return ResponseEntity.ok(ApiResult.success(response));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResult<Page<ProductResponse>>> searchProducts(
            @RequestParam String keyword,
            Pageable pageable) {

        Page<Product> products = productService.searchProducts(keyword, pageable);
        Page<ProductResponse> response = products.map(this::convertToResponse);

        return ResponseEntity.ok(ApiResult.success(response));
    }

    @GetMapping("/featured")
    public ResponseEntity<ApiResult<List<ProductResponse>>> getFeaturedProducts() {
        List<Product> products = productService.findFeaturedProducts();
        List<ProductResponse> response = products.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResult.success(response));
    }

    @GetMapping("/bestsellers")
    public ResponseEntity<ApiResult<List<ProductResponse>>> getBestSellers(
            @RequestParam(defaultValue = "10") int limit) {

        List<Product> products = productService.findBestSellers(limit);
        List<ProductResponse> response = products.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResult.success(response));
    }

    private ProductResponse convertToResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setProductId(product.getProductId());
        response.setProductCode(product.getProductCode());
        response.setProductName(product.getProductName());
        response.setProductNameEn(product.getProductNameEn());
        response.setBrand(product.getBrand());
        response.setDescription(product.getDescription());
        response.setDescriptionEn(product.getDescriptionEn());
        response.setProductStatus(product.getProductStatus());
        response.setPrice(product.getPrice());
        response.setSalePrice(product.getSalePrice());
        response.setCurrency(product.getCurrency());
        response.setAvgRating(product.getAvgRating());
        response.setReviewCount(product.getReviewCount());
        response.setSalesCount(product.getSalesCount());
        response.setViewCount(product.getViewCount());
        response.setWeightGram(product.getWeightGram());
        response.setIsFeatured(product.getIsFeatured());
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());

        // 카테고리 정보
        if (product.getCategory() != null) {
            ProductResponse.CategorySummary categorySummary = new ProductResponse.CategorySummary();
            categorySummary.setCategoryId(product.getCategory().getCategoryId());
            categorySummary.setCategoryName(product.getCategory().getCategoryName());
            categorySummary.setSlug(product.getCategory().getSlug());
            response.setCategory(categorySummary);
        }

        return response;
    }

    private Product convertToEntity(ProductCreateRequest request) {
        Product product = new Product();
        product.setProductCode(request.getProductCode());
        product.setProductName(request.getProductName());
        product.setProductNameEn(request.getProductNameEn());
        product.setBrand(request.getBrand());
        product.setDescription(request.getDescription());
        product.setDescriptionEn(request.getDescriptionEn());
        product.setProductStatus(request.getProductStatus());
        product.setPrice(request.getPrice());
        product.setSalePrice(request.getSalePrice());
        product.setCurrency(request.getCurrency());
        product.setWeightGram(request.getWeightGram());
        product.setIsFeatured(request.getIsFeatured());

        // 카테고리 설정
        if (request.getCategoryId() != null) {
            product.setCategory(categoryService.findById(request.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다.")));
        }

        return product;
    }
}
