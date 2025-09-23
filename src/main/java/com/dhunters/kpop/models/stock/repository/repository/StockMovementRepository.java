package com.dhunters.kpop.models.stock.repository.repository;

import com.dhunters.kpop.common.entity.stock.StockMovement;
import com.dhunters.kpop.models.stock.repository.projection.TransferHistoryView;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    // 3) 창고<->매장 이동 이력 (TRANSFER 전용)
    @Query(value = """
        SELECT 
          m.id               AS id,
          m.product_item_id  AS productItemId,
          m.from_location_id AS fromLocationId,
          lf.code            AS fromLocationCode,
          m.to_location_id   AS toLocationId,
          lt.code            AS toLocationCode,
          m.quantity         AS quantity,
          m.moved_at         AS movedAt,
          m.ref_type         AS refType,
          m.ref_id           AS refId,
          m.reason           AS reason
        FROM stock_movement m
        LEFT JOIN location lf ON lf.id = m.from_location_id
        LEFT JOIN location lt ON lt.id = m.to_location_id
        WHERE m.type = 'TRANSFER'
          AND m.product_item_id = :productItemId
        ORDER BY m.moved_at DESC
        """,
            countQuery = """
        SELECT COUNT(*)
          FROM stock_movement m
         WHERE m.type = 'TRANSFER'
           AND m.product_item_id = :productItemId
        """,
            nativeQuery = true)
    Page<TransferHistoryView> findTransferHistory(@Param("productItemId") Long productItemId, Pageable pageable);
}
