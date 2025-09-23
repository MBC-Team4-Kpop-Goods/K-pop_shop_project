package com.dhunters.kpop.common.entity.stock;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "location", indexes = {
        @Index(name = "idx_location_code", columnList = "code")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Location {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 창고/매장 위치 코드 */
    @Column(length = 50)
    private String code;

    /** 위치명 (예: 서울창고 1동) */
    @Column(length = 100)
    private String name;

    /** 위치 구분 */
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private LocationType type; // WAREHOUSE, STORE, TRANSFER

    /** 사용 여부 */
    @Column
    private Boolean active;

    /** 비고 */
    @Column(length = 500)
    private String note;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime modifiedAt;

    @PrePersist
    public void prePersist() {
        if (type == null) type = LocationType.WAREHOUSE;
        if (active == null) active = true;
        var now = LocalDateTime.now();
        if (createdAt == null) createdAt = now;
        if (modifiedAt == null) modifiedAt = now;
    }

    @PreUpdate
    public void preUpdate() { modifiedAt = LocalDateTime.now(); }

    public enum LocationType { WAREHOUSE, STORE, TRANSIT }
    // 창고 / 가게 / 이동 중
}