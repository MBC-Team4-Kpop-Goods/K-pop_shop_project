package com.dhunters.kpop.models.stock.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StockWhereReq {
    // 상품 변형 pk
    private Long productItemId;
}