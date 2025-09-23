package com.dhunters.kpop.models.stock.repository.projection;

public interface StockWhereView {
    Long   getLocationId();
    String getLocationCode();
    String getLocationName();
    String getLocationType(); // 'WAREHOUSE' / 'STORE' / ...
    Integer getOnHand();
}
