package com.dhunters.kpop.dto.wishlist;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class WishListResponse {

    private Long memberId;
    private Long productId;
    private String productName;
    private String productImage;
    private BigDecimal price;
    private BigDecimal salePrice;
    private String productStatus;
    private Boolean isInStock;
    private Date addedAt;
}

