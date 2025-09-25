package com.dhunters.kpop.models.stock.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StockCountsRes {
    private Long productItemId;

    /** 타입별 합계 (간단 명시형) */
    private Integer warehouse; // WAREHOUSE 전체 합
    private Integer store;     // STORE 전체 합
    private Integer other;     // 기타 합
    // (창고/매장 제외. 지금은 transit밖에 없는데 나중에는 상태 추가될 수도 있으니까)
    private Integer total;     // 전체 합계
}