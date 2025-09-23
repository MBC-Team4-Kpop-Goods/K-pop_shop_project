package com.dhunters.kpop.models.stock.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StockCountsReq {
    private Long productItemId;
}