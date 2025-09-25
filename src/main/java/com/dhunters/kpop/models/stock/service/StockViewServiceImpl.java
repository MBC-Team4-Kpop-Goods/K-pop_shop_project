package com.dhunters.kpop.models.stock.service;

import com.dhunters.kpop.models.stock.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockViewServiceImpl implements com.dhunters.kpop.models.stock.service.StockViewService {
    @Override
    public List<StockWhereRes> findWhere(StockWhereReq req) {
        return List.of();
    }

    @Override
    public StockCountsRes sumCounts(StockCountsReq req) {
        return null;
    }

    @Override
    public Page<TransferHistoryRes> findTransfers(TransferHistoryReq req) {
        return null;
    }
}