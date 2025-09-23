package com.dhunters.kpop.dto.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductOptionCreateRequest {

    @NotBlank(message = "옵션값은 필수입니다.")
    private String optionValue;

    private String optionValueEn;

    @DecimalMin(value = "0.0", message = "가격 조정값은 0 이상이어야 합니다.")
    private BigDecimal priceAdjustment = BigDecimal.ZERO;

    @NotBlank(message = "SKU는 필수입니다.")
    private String sku;

    @Min(value = 0, message = "재고 수량은 0 이상이어야 합니다.")
    private Integer stockQty = 0;

    @Min(value = 0, message = "무게는 0 이상이어야 합니다.")
    private Integer weightGram;

    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "올바른 색상 코드 형식이 아닙니다. (#RRGGBB)")
    private String colorHex;

    private Integer sortOrder = 0;

    private Boolean isActive = true;
}

