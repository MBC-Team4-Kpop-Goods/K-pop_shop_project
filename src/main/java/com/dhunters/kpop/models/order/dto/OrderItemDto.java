package com.dhunters.kpop.models.order.dto;


import com.dhunters.kpop.common.enums.OrderStatus;
import lombok.*;
import java.math.BigDecimal;

/**
 * 주문 아이템 정보를 담는 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {

    /** 주문 아이템 고유 ID */
    private Long orderItemId;

    /** 상품 변형 ID */
    private Long variantId;

    /** 상품 정보 */
    private String productName;
    private String variantInfo; // 색상, 사이즈 등
    private String optionInfo;  // 추가 옵션 정보

    /** 수량 및 가격 */
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    /** 아이템 상태 */
    private OrderStatus.ItemStatus status;
}

