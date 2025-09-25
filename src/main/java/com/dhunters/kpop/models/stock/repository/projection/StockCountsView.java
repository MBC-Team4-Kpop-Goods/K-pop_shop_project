package com.dhunters.kpop.models.stock.repository.projection;

public interface StockCountsView {
    Long getProductItemId();
    Integer getWarehouse();
    Integer getStore();
    Integer getOther();
    Integer getTotal();
}
