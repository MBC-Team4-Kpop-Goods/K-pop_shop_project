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
    @Query(value = """
        SELECT l.id     AS locationId,
               l.code   AS locationCode,
               l.name   AS locationName,
               l.type   AS locationType,
               i.on_hand AS onHand
          FROM inventory i
          JOIN location  l ON l.id = i.location_id
         WHERE i.product_item_id = :productItemId
           AND COALESCE(i.on_hand, 0) > 0
         ORDER BY l.type, l.code
        """, nativeQuery = true)
    List<StockWhereView> findStockWhere(@Param("productItemId") Long productItemId);

    // 2) 창고/매장/기타 별 재고 합계
    @Query(value = """
        SELECT
          :productItemId AS productItemId,
          SUM(CASE WHEN l.type = 'WAREHOUSE' THEN COALESCE(i.on_hand,0) ELSE 0 END) AS warehouse,
          SUM(CASE WHEN l.type = 'STORE'     THEN COALESCE(i.on_hand,0) ELSE 0 END) AS store,
          SUM(CASE WHEN l.type NOT IN ('WAREHOUSE','STORE') THEN COALESCE(i.on_hand,0) ELSE 0 END) AS other,
          SUM(COALESCE(i.on_hand,0)) AS total
        FROM inventory i
        JOIN location  l ON l.id = i.location_id
        WHERE i.product_item_id = :productItemId
        """, nativeQuery = true)
    StockCountsView sumCountsByType(@Param("productItemId") Long productItemId);
}
