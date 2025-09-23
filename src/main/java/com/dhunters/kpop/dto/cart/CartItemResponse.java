package com.dhunters.kpop.dto.cart;

import com.dhunters.kpop.dto.product.ProductOptionResponse;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class CartItemResponse {

    private Long cartItemId;
    private Long productId;
    private String productName;
    private String productImage;
    private BigDecimal productPrice;
    private ProductOptionResponse option;
    private Integer qty;
    private BigDecimal itemTotal;
    private Date addedAt;
}

