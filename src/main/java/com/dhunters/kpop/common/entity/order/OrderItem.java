package com.dhunters.kpop.common.entity.order;


import com.dhunters.kpop.common.entity.BaseEntity;
// TODO: ProductVariant 구현 후 import 추가
// import com.dhunters.kpop.common.entity.product.ProductVariant;
import com.dhunters.kpop.common.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * 주문 상품 엔티티
 * order_item 테이블과 매핑
 */
@Entity
@Table(name = "order_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderInfo orderInfo;

    // TODO: ProductVariant 구현 후 활성화
    @Column(name = "variant_id", nullable = false)
    private Long variantId; // 임시로 ID만 저장

    @Column(name = "product_name", nullable = false, length = 200)
    private String productName;

    @Column(name = "variant_info", nullable = false, length = 100)
    private String variantInfo; // 색상, 사이즈 등 옵션 정보

    @Column(name = "option_info", columnDefinition = "TEXT")
    private String optionInfo; // JSON 형태의 추가 옵션 정보

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "total_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private OrderStatus.ItemStatus status = OrderStatus.ItemStatus.NORMAL;

    // 총 가격 계산 메서드
    public void calculateTotalPrice() {
        this.totalPrice = this.unitPrice.multiply(BigDecimal.valueOf(this.quantity));
    }


}