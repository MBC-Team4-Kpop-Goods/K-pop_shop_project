package com.dhunters.kpop.common.entity.stock;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movement", indexes = {
        @Index(name = "idx_movement_moved_at", columnList = "moved_at"),
        @Index(name = "idx_movement_item",    columnList = "product_item_id")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StockMovement {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING) @Column(length = 20)
    private MovementType type; // INBOUND, OUTBOUND, TRANSFER, ADJUSTMENT

    @Column(name = "product_item_id")
    private Long productItemId;

    @Column(name = "from_location_id")
    private Long fromLocationId;

    @Column(name = "to_location_id")
    private Long toLocationId;

    @Column private Integer quantity;
    @Column(length = 30)  private String refType;
    @Column(length = 100) private String refId;

    @Enumerated(EnumType.STRING) @Column(length = 20)
    private MovementStatus status; // PENDING, APPLIED, CANCELLED

    @Column(length = 500) private String reason;

    @Column(name = "moved_at") private LocalDateTime movedAt;

    @PrePersist
    public void prePersist() {
        if (status == null) status = MovementStatus.APPLIED;
        if (movedAt == null) movedAt = LocalDateTime.now();
        if (type == null) type = MovementType.ADJUSTMENT;
        if (quantity == null) quantity = 0;
    }

    public enum MovementType { INBOUND, OUTBOUND, TRANSFER, ADJUSTMENT }
    public enum MovementStatus { PENDING, APPLIED, CANCELLED }
}