package com.dhunters.kpop.common.entity.cart;


import com.dhunters.kpop.common.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString(exclude = {"cart", "product", "option"})
@Table(
        name = "cart_item",
        uniqueConstraints = {
                @UniqueConstraint(name = "ux_cartitem_cart_product_option",
                        columnNames = {"cart_id", "product_id", "option_id"})
        },
        indexes = {
                @Index(name = "ix_cartitem_cart", columnList = "cart_id"),
                @Index(name = "ix_cartitem_product", columnList = "product_id")
        }
)
public class CartItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long cartItemId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_cartitem_cart"))
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_cartitem_product"))
    private com.dhunters.kpop.common.entity.product.Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "option_id",
            foreignKey = @ForeignKey(name = "fk_cartitem_option"))
    private com.dhunters.kpop.common.entity.product.ProductOption option; // 옵션 없는 경우 null

    @Column(name = "qty", nullable = false)
    private Integer qty = 1;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "added_at", nullable = false, updatable = false)
    private Date addedAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt = new Date();
}
