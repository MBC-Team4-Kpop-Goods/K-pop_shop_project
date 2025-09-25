package com.dhunters.kpop.common.entity.order;

import com.dhunters.kpop.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

 //배송 정보 엔티티 shipping 테이블과 매핑

@Entity
@Table(name = "shipping")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipping extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipping_id")
    private Long shippingId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderInfo orderInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "shipping_status", nullable = false, length = 20)
    @Builder.Default
    private ShippingStatus shippingStatus = ShippingStatus.PREPARING;

    @Column(name = "tracking_number", length = 50)
    private String trackingNumber;

    @Column(name = "courier_company", length = 50)
    private String courierCompany;

    @Column(name = "order_memo", columnDefinition = "TEXT")
    private String orderMemo;

    // 배송 상태 Enum
    public enum ShippingStatus {
        PREPARING,   // 배송 준비
        SHIPPED,     // 배송 중
        IN_TRANSIT,  // 운송 중
        DELIVERED    // 배송 완료
    }
}