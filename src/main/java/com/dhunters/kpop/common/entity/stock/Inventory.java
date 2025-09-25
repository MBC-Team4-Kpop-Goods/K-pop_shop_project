package com.dhunters.kpop.common.entity.stock;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory", indexes = {
        @Index(name = "idx_inventory_location", columnList = "location_id"),
        @Index(name = "idx_inventory_item",     columnList = "product_item_id")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Inventory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // fk 제약 없음
    @Column(name = "location_id")
    private Long locationId;

    /** SKU(Product Item) PK */
    @Column(name = "product_item_id")
    private Long productItemId;

    @Column(name = "on_hand")
    private Integer onHand;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (onHand == null) onHand = 0;
        if (updatedAt == null) updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() { updatedAt = LocalDateTime.now(); }

    @Transient
    public int getAvailable() { return onHand == null ? 0 : onHand; }
}
