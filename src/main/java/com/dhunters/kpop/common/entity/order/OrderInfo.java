package com.dhunters.kpop.common.entity.order;

import com.dhunters.kpop.common.entity.BaseEntity;
import com.dhunters.kpop.common.entity.Member;
import com.dhunters.kpop.common.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 주문 정보 엔티티
 * order_info 테이블과 매핑
 */
@Entity
@Table(name = "order_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_number", nullable = false, length = 50)
    private String orderNumber;

    // TODO: Member 구현 후 활성화
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = true) // 임시로 nullable
    private Member member;

    @Column(name = "orderer_name", nullable = false, length = 50)
    private String ordererName;

    @Column(name = "orderer_phone", nullable = false, length = 20)
    private String ordererPhone;

    @Column(name = "orderer_email", nullable = false, length = 100)
    private String ordererEmail;

    @Column(name = "shipping_address", nullable = false, columnDefinition = "TEXT")
    private String shippingAddress;

    @Column(name = "total_product_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalProductAmount;

    @Column(name = "shipping_fee", nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal shippingFee = BigDecimal.ZERO;

    @Column(name = "discount_amount", nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(name = "final_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal finalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false, length = 20)
    @Builder.Default
    private OrderStatus.Status orderStatus = OrderStatus.Status.PENDING;

    // 연관관계 - OrderItem과 1:N
    @OneToMany(mappedBy = "orderInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    // 연관관계 - Shipping과 1:1
    @OneToOne(mappedBy = "orderInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Shipping shipping;



}