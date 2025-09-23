package com.dhunters.kpop.dto.cart;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CartResponse {

    private Long cartId;
    private Long memberId;
    private String sessionId;
    private Date expiresAt;
    private List<CartItemResponse> items;
    private BigDecimal totalAmount;
    private Integer totalItems;
    private Date createdAt;
    private Date updatedAt;
}

