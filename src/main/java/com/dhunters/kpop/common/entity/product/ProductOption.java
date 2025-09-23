package com.dhunters.kpop.common.entity.product;


import com.dhunters.kpop.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString(exclude = "product")
@Table(
        name = "product_option",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_product_option_sku", columnNames = {"sku"}),
                @UniqueConstraint(name = "uq_product_option_product_value", columnNames = {"product_id", "option_value"})
        },
        indexes = {
                @Index(name = "ix_product_option_product", columnList = "product_id")
        }
)
public class ProductOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long optionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_product_option_product"))
    private Product product;

    @Column(name = "option_value", nullable = false, length = 100)
    private String optionValue;

    @Column(name = "option_value_en", length = 100)
    private String optionValueEn;

    @Column(name = "price_adjustment", nullable = false, precision = 15, scale = 2)
    private BigDecimal priceAdjustment = BigDecimal.ZERO;

    @Column(name = "sku", nullable = false, length = 80)
    private String sku;

    @Column(name = "stock_qty", nullable = false)
    private Integer stockQty = 0;

    @Column(name = "weight_gram")
    private Integer weightGram;

    @Column(name = "color_hex", length = 7)
    private String colorHex; // "#RRGGBB"

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;



    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    private Date deletedAt;
}
