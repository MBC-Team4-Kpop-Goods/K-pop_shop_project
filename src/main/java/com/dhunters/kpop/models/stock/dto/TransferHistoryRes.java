package com.dhunters.kpop.models.stock.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TransferHistoryRes {
    private Long id;
    private Long productItemId;
    private Long fromLocationId;
    private String fromLocationCode;
    private Long toLocationId;
    private String toLocationCode;
    private Integer quantity;
    private LocalDateTime movedAt;
    private String refType;  // MOVE/ORDER 등
    private String refId;
    private String reason;
}
