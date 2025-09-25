package com.dhunters.kpop.models.stock.repository.repository;

import com.dhunters.kpop.common.entity.stock.Inventory;
import com.dhunters.kpop.models.stock.repository.projection.StockCountsView;
import com.dhunters.kpop.models.stock.repository.projection.StockWhereView;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // 기본 조회
    Optional<Inventory> findByLocationIdAndProductItemId(Long locationId, Long productItemId);
    List<Inventory> findByLocationId(Long locationId);
    List<Inventory> findByProductItemId(Long productItemId);

    // 1) 이 SKU가 어디(창고/매장)에 얼마나 있나


    // 2) 창고/매장/기타 별 재고 합계

}
