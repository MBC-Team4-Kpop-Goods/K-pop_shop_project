package com.dhunters.kpop.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductOptionResponse {

    private Long optionId;
    private String optionValue;
    private String optionValueEn;
    private BigDecimal priceAdjustment;
    private String sku;
    private Integer stockQty;
    private Integer weightGram;
    private String colorHex;
    private Integer sortOrder;
    private Boolean isActive;
}

