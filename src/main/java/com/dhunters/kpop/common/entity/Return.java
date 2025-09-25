package com.dhunters.kpop.common.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "re_turn")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Return {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "return_id")
    private Long returnId;   // 반품 ID (PK)

    @Column(name = "order_item_id", nullable = false)
    private Long orderItemId;   // 주문상품 ID (FK)

    @Column(name = "member_id", nullable = false)
    private Long memberId;   // 회원 ID (FK)

    @Column(name = "return_reason", length = 100)
    private String returnReason;   // 반품 사유

    @Column(name = "return_detail", columnDefinition = "TEXT")
    private String returnDetail;   // 상세 반품 사유

    @Column(name = "return_quantity", nullable = false)
    private Integer returnQuantity;   // 반품 수량

    @Column(name = "return_amount", precision = 12, scale = 2, nullable = false)
    private Double returnAmount;   // 반품 금액

    @Column(name = "return_status", length = 20, nullable = false)
    private String returnStatus;   // 반품 상태 (REQUESTED, APPROVED, REJECTED, COMPLETED)

    @Column(name = "pickup_address", columnDefinition = "TEXT")
    private String pickupAddress;   // 수거 주소

    @Column(name = "pickup_date")
    private LocalDateTime pickupDate;   // 수거 예정일

    @Column(name = "refund_method", length = 20)
    private String refundMethod;   // 환불 방법

    @Column(name = "refund_account", length = 200)
    private String refundAccount;   // 환불 계좌 정보

    @Column(name = "process_memo", columnDefinition = "TEXT")
    private String processMemo;   // 처리 메모



    // 연관관계 매핑 (선택)
    // 주문상품, 회원 엔티티와 연결 가능
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "order_item_id", insertable = false, updatable = false)
    // private OrderItem orderItem;
    //
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "member_id", insertable = false, updatable = false)
    // private Member member;
}
