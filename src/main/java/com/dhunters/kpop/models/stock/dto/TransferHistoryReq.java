package com.dhunters.kpop.models.stock.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TransferHistoryReq {
    private Long productItemId; // 대상 SKU
    private Integer page;       // 페이징 (옵션)
    private Integer size;       // 페이징 (옵션)
}