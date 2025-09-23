package com.dhunters.kpop.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ProductResponse {

    private Long productId;
    private String productCode;
    private String productName;
    private String productNameEn;
    private String brand;
    private String description;
    private String descriptionEn;
    private String productStatus;
    private BigDecimal price;
    private BigDecimal salePrice;
    private String currency;
    private BigDecimal avgRating;
    private Integer reviewCount;
    private Integer salesCount;
    private Integer viewCount;
    private Integer weightGram;
    private Boolean isFeatured;
    private Date createdAt;
    private Date updatedAt;

    // 카테고리 정보
    private CategorySummary category;

    // 상품 옵션들
    private List<ProductOptionResponse> options;

    @Getter
    @Setter
    public static class CategorySummary {
        private Long categoryId;
        private String categoryName;
        private String slug;
    }
}

