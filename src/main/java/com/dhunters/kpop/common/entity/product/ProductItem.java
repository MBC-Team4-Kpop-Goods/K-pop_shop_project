package com.dhunters.kpop.common.entity.product;

import com.dhunters.kpop.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(
        name = "product_item",
        indexes = {
                @Index(name = "ix_product_item_sku", columnList = "sku", unique = true),
                @Index(name = "ix_product_item_product", columnList = "product_id")
        }
)
@Getter
@Setter
@ToString(exclude = {"product"})
public class ProductItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_item_id")
    private Long id;

    /** 어떤 상품의 단품인지 */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_product_item_product"))
    @JsonIgnore // 엔티티를 직접 반환하는 경우 무한 루프 방지. 우리는 주로 DTO로 반환할 예정.
    private Product product;

    /** 재고/주문에서 식별에 쓰는 고유값 */
    @Column(name = "sku", nullable = false, length = 64, unique = true)
    private String sku;

    /** 옵션 값 간단 버전(예: 색상/사이즈) — 필요시 더 추가 가능 */
    @Column(name = "option1_value", length = 100)
    private String option1Value;

    @Column(name = "option2_value", length = 100)
    private String option2Value;

    /** 단품 판매가(상품 기본가를 덮어씀) */
    @Column(name = "price", nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    /** 가용 재고 수량 */
    @Column(name = "stock", nullable = false)
    private Integer stock = 0;

    /** 노출/판매 여부 */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}
