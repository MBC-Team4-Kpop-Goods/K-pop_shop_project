package com.dhunters.kpop.models.stock.repository.projection;

import java.time.LocalDateTime;

public interface TransferHistoryView {
    Long getId();
    Long getProductItemId();
    Long getFromLocationId();
    String getFromLocationCode();
    Long getToLocationId();
    String getToLocationCode();
    Integer getQuantity();
    LocalDateTime getMovedAt();
    String getRefType();
    String getRefId();
    String getReason();
}
