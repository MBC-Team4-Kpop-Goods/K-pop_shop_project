package com.dhunters.kpop.models.stock.service;

import com.dhunters.kpop.models.stock.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StockViewService {

    /** 1) 특정 상품이 어디(창고/매장)에 얼마나 있나 */
    List<StockWhereRes> findWhere(StockWhereReq req);

    /** 2) 창고/매장/기타 별 재고 합계 */
    StockCountsRes sumCounts(StockCountsReq req);

    /** 3) 창고 <-> 매장 이동 이력 (TRANSFER) */
    Page<TransferHistoryRes> findTransfers(TransferHistoryReq req);
}
