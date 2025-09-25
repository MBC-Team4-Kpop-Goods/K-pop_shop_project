package com.dhunters.kpop.models.stock.repository.repository;

import com.dhunters.kpop.common.entity.stock.StockMovement;
import com.dhunters.kpop.models.stock.repository.projection.TransferHistoryView;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    // 3) 창고<->매장 이동 이력 (TRANSFER 전용)

    Page<TransferHistoryView> findTransferHistory(@Param("productItemId") Long productItemId, Pageable pageable);
}
