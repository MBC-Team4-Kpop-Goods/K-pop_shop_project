package com.dhunters.kpop.models.order.dto;

import com.dhunters.kpop.common.entity.order.Shipping;
import lombok.*;

/**
 * 배송 정보를 담는 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingDto {

    /** 배송 ID */
    private Long shippingId;

    /** 배송 상태 */
    private Shipping.ShippingStatus shippingStatus;

    /** 택배 정보 */
    private String trackingNumber;
    private String courierCompany;

    /** 주문 메모 */
    private String orderMemo;
}
