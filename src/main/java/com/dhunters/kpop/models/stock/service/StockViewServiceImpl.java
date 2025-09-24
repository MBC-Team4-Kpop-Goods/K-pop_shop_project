package com.dhunters.kpop.models.stock.service;

import com.dhunters.kpop.common.entity.stock.Location;
import com.dhunters.kpop.models.stock.dto.*;
import com.dhunters.kpop.models.stock.repository.repository.InventoryRepository;
import com.dhunters.kpop.models.stock.repository.repository.StockMovementRepository;
import com.dhunters.kpop.models.stock.repository.projection.StockCountsView;
import com.dhunters.kpop.models.stock.repository.projection.StockWhereView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StockViewServiceImpl implements com.dhunters.kpop.models.stock.service.StockViewService {

    private final InventoryRepository inventoryRepository;
    private final StockMovementRepository stockMovementRepository;

    // 1) 어디(창고/매장)에 있나
    @Override
    public List<StockWhereRes> findWhere(StockWhereReq req) {
        Long pid = req.getProductItemId();
        List<StockWhereView> views = inventoryRepository.findStockWhere(pid);

        return views.stream()
                .map(v -> StockWhereRes.builder()
                        .locationId(v.getLocationId())
                        .locationCode(v.getLocationCode())
                        .locationName(v.getLocationName())
                        .locationType(safeType(v.getLocationType()))
                        .onHand(nz(v.getOnHand()))
                        .build())
                .toList();
    }

    // 2) 창고/매장/기타 합계
    @Override
    public StockCountsRes sumCounts(StockCountsReq req) {
        Long pid = req.getProductItemId();
        StockCountsView v = inventoryRepository.sumCountsByType(pid);

        return StockCountsRes.builder()
                .productItemId(pid)
                .warehouse(nz(v == null ? null : v.getWarehouse()))
                .store(nz(v == null ? null : v.getStore()))
                .other(nz(v == null ? null : v.getOther()))
                .total(nz(v == null ? null : v.getTotal()))
                .build();
    }

    // 3) 창고 <-> 매장 이동 이력
    @Override
    public Page<TransferHistoryRes> findTransfers(TransferHistoryReq req) {
        int page = req.getPage() == null ? 0 : Math.max(0, req.getPage());
        int size = req.getSize() == null ? 20 : Math.max(1, req.getSize());
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "movedAt"));

        return stockMovementRepository
                .findTransferHistory(req.getProductItemId(), pageable)
                .map(v -> TransferHistoryRes.builder()
                        .id(v.getId())
                        .productItemId(v.getProductItemId())
                        .fromLocationId(v.getFromLocationId())
                        .fromLocationCode(v.getFromLocationCode())
                        .toLocationId(v.getToLocationId())
                        .toLocationCode(v.getToLocationCode())
                        .quantity(nz(v.getQuantity()))
                        .movedAt(v.getMovedAt())
                        .refType(v.getRefType())
                        .refId(v.getRefId())
                        .reason(v.getReason())
                        .build());
    }

    //
    private static int nz(Integer v) { return v == null ? 0 : v; }

    private static Location.LocationType safeType(String type) {
        if (type == null) return null;
        try {
            return Location.LocationType.valueOf(type);
        } catch (IllegalArgumentException ex) {
            return null; // 예상 외 값이면 null로
        }
    }
}
