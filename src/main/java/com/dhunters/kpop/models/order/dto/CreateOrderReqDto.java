package com.dhunters.kpop.models.order.dto;

import lombok.*;
import java.math.BigDecimal;

/**
 * 주문 생성 요청용 DTO
 * 장바구니에서 주문으로 변환할 때 사용
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderReqDto {

    /** 장바구니 ID (장바구니 → 주문 변환) */
    private Long cartId;

    /** 주문자 정보 */
    private String ordererName;
    private String ordererPhone;
    private String ordererEmail;

    /** 배송지 정보 */
    private String shippingAddress;

    /** 할인 금액 (쿠폰, 적립금 등) */
    private BigDecimal discountAmount;

    /** 주문 메모 */
    private String orderMemo;
}