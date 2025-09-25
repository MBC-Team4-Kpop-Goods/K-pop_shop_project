package com.dhunters.kpop.models.order.dto;

import com.dhunters.kpop.common.enums.OrderStatus;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

/** 메인 응답용 (주문 전체 정보)
 * 주문 전체 정보를 담는 DTO
 * 주문 조회, 생성, 수정 후 응답에 사용
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {


    private Long orderId; //주문ID

    private String orderNumber; //주문번호

    private Long memberId;  //회원 ID

    private String ordererName; // 주문자명
    private String ordererPhone; //주문자 연락처
    private String ordererEmail; //주문자 이메일


   private String shippingAddress; // 배송지 정보

    /** 금액 정보 */
    private BigDecimal totalProductAmount;
    private BigDecimal shippingFee;
    private BigDecimal discountAmount;
    private BigDecimal finalAmount;

    /** 주문 상태 */
    private OrderStatus.Status orderStatus;

    /** 주문 아이템 목록 */
    private List<OrderItemDto> orderItems;

    /** 배송 정보 */
    private ShippingDto shipping;

    /** 생성일시 */
    private String createdAt;
}
