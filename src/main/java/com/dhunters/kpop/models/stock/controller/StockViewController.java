package com.dhunters.kpop.models.stock.controller;

import com.dhunters.kpop.core.api.ApiResult;
import com.dhunters.kpop.models.stock.dto.StockCountsReq;
import com.dhunters.kpop.models.stock.dto.StockCountsRes;
import com.dhunters.kpop.models.stock.dto.StockWhereReq;
import com.dhunters.kpop.models.stock.dto.StockWhereRes;
import com.dhunters.kpop.models.stock.dto.TransferHistoryReq;
import com.dhunters.kpop.models.stock.dto.TransferHistoryRes;
import com.dhunters.kpop.models.stock.service.StockViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class StockViewController {

    /**
     # A. Where GET
     curl "http://localhost:81/api/stock/where?productItemId=1001"

     # B. Counts GET
     curl "http://localhost:81/api/stock/counts?productItemId=1001"

     # C. Transfers (paged) GET
     curl "http://localhost:81/api/stock/transfers?productItemId=1001&page=0&size=20"
     */

    private final StockViewService stockViewService;

    /** 1) 특정 SKU가 어디(창고/매장)에 얼마나 있는지 */
    @GetMapping("/where")
    public ApiResult<List<StockWhereRes>> where(@ModelAttribute StockWhereReq req) {
        List<StockWhereRes> data = stockViewService.findWhere(req);
        return new ApiResult<>(data);
    }

    /** 2) 창고/매장/기타 별 재고 합계 */
    @GetMapping("/counts")
    public ApiResult<StockCountsRes> counts(@ModelAttribute StockCountsReq req) {
        StockCountsRes data = stockViewService.sumCounts(req);
        return new ApiResult<>(data);
    }

    /** 3) 창고 <-> 매장 이동 이력 (TRANSFER) */
    @GetMapping("/transfers")
    public ApiResult<Page<TransferHistoryRes>> transfers(@ModelAttribute TransferHistoryReq req) {
        Page<TransferHistoryRes> data = stockViewService.findTransfers(req);
        return new ApiResult<>(data);
    }
}
