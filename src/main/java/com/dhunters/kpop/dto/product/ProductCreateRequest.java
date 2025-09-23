package com.dhunters.kpop.dto.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductCreateRequest {

    @NotNull(message = "카테고리 ID는 필수입니다.")
    private Long categoryId;

    @NotBlank(message = "상품 코드는 필수입니다.")
    private String productCode;

    @NotBlank(message = "상품명은 필수입니다.")
    private String productName;

    private String productNameEn;

    private String brand;

    private String description;

    private String descriptionEn;

    @NotBlank(message = "상품 상태는 필수입니다.")
    private String productStatus = "ACTIVE";

    @NotNull(message = "가격은 필수입니다.")
    @DecimalMin(value = "0.0", message = "가격은 0 이상이어야 합니다.")
    private BigDecimal price;

    @DecimalMin(value = "0.0", message = "할인가격은 0 이상이어야 합니다.")
    private BigDecimal salePrice;

    private String currency = "KRW";

    private Integer weightGram;

    private Boolean isFeatured = false;
}
