package com.dhunters.kpop.models.stock.dto;

import com.dhunters.kpop.common.entity.stock.Location.LocationType;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StockWhereRes {
    private Long locationId;
    private String locationCode;
    private String locationName;
    private LocationType locationType; // WAREHOUSE / STORE ...
    private Integer onHand;            // 해당 위치의 보유 수량
}