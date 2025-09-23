package com.dhunters.kpop.service;

import com.dhunters.kpop.common.entity.Product;
import com.dhunters.kpop.common.entity.ProductOption;
import com.dhunters.kpop.repository.ProductOptionRepository;
import com.dhunters.kpop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductOptionService {

    private final ProductOptionRepository productOptionRepository;
    private final ProductRepository productRepository;

    public List<ProductOption> findOptionsByProductId(Long productId) {
        return productOptionRepository.findByProductIdOrderBySortOrder(productId);
    }

    public List<ProductOption> findActiveOptionsByProductId(Long productId) {
        return productOptionRepository.findActiveByProductId(productId);
    }

    public Optional<ProductOption> findById(Long optionId) {
        return productOptionRepository.findActiveById(optionId);
    }

    public Optional<ProductOption> findBySku(String sku) {
        return productOptionRepository.findBySku(sku);
    }

    @Transactional
    public ProductOption createOption(Long productId, ProductOption option) {
        Product product = productRepository.findActiveById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        // SKU 중복 체크
        if (productOptionRepository.existsBySkuAndDeletedAtIsNull(option.getSku())) {
            throw new IllegalArgumentException("이미 존재하는 SKU입니다: " + option.getSku());
        }

        option.setProduct(product);

        // 정렬 순서 설정 (마지막에 추가)
        if (option.getSortOrder() == null) {
            List<ProductOption> existingOptions = findOptionsByProductId(productId);
            option.setSortOrder(existingOptions.size());
        }

        option.setCreatedAt(new Date());
        option.setUpdatedAt(new Date());

        return productOptionRepository.save(option);
    }

    @Transactional
    public ProductOption updateOption(Long productId, Long optionId, ProductOption updateInfo) {
        ProductOption option = productOptionRepository.findByProductIdAndOptionId(productId, optionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 옵션입니다."));

        // SKU 중복 체크 (현재 옵션 제외)
        if (updateInfo.getSku() != null && !option.getSku().equals(updateInfo.getSku())) {
            if (productOptionRepository.existsBySkuAndDeletedAtIsNull(updateInfo.getSku())) {
                throw new IllegalArgumentException("이미 존재하는 SKU입니다: " + updateInfo.getSku());
            }
            option.setSku(updateInfo.getSku());
        }

        if (updateInfo.getOptionValue() != null) {
            option.setOptionValue(updateInfo.getOptionValue());
        }
        if (updateInfo.getOptionValueEn() != null) {
            option.setOptionValueEn(updateInfo.getOptionValueEn());
        }
        if (updateInfo.getPriceAdjustment() != null) {
            option.setPriceAdjustment(updateInfo.getPriceAdjustment());
        }
        if (updateInfo.getStockQty() != null) {
            option.setStockQty(updateInfo.getStockQty());
        }
        if (updateInfo.getWeightGram() != null) {
            option.setWeightGram(updateInfo.getWeightGram());
        }
        if (updateInfo.getColorHex() != null) {
            option.setColorHex(updateInfo.getColorHex());
        }
        if (updateInfo.getSortOrder() != null) {
            option.setSortOrder(updateInfo.getSortOrder());
        }
        if (updateInfo.getIsActive() != null) {
            option.setIsActive(updateInfo.getIsActive());
        }

        option.setUpdatedAt(new Date());
        return productOptionRepository.save(option);
    }

    @Transactional
    public void deleteOption(Long productId, Long optionId) {
        ProductOption option = productOptionRepository.findByProductIdAndOptionId(productId, optionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 옵션입니다."));

        // 소프트 삭제
        option.setDeletedAt(new Date());
        productOptionRepository.save(option);
    }

    @Transactional
    public void updateStock(Long optionId, Integer quantity) {
        ProductOption option = productOptionRepository.findActiveById(optionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 옵션입니다."));

        int newStock = option.getStockQty() + quantity;
        if (newStock < 0) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }

        option.setStockQty(newStock);
        option.setUpdatedAt(new Date());
        productOptionRepository.save(option);
    }

    public boolean isInStock(Long optionId, Integer requestedQty) {
        ProductOption option = productOptionRepository.findActiveById(optionId)
                .orElse(null);

        return option != null && option.getIsActive() && option.getStockQty() >= requestedQty;
    }
}

