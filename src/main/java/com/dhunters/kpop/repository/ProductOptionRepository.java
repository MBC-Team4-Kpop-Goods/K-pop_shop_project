package com.dhunters.kpop.repository;

import com.dhunters.kpop.common.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    @Query("SELECT po FROM ProductOption po WHERE po.product.productId = :productId AND po.deletedAt IS NULL ORDER BY po.sortOrder")
    List<ProductOption> findByProductIdOrderBySortOrder(@Param("productId") Long productId);

    @Query("SELECT po FROM ProductOption po WHERE po.product.productId = :productId AND po.isActive = true AND po.deletedAt IS NULL ORDER BY po.sortOrder")
    List<ProductOption> findActiveByProductId(@Param("productId") Long productId);

    @Query("SELECT po FROM ProductOption po WHERE po.optionId = :optionId AND po.deletedAt IS NULL")
    Optional<ProductOption> findActiveById(@Param("optionId") Long optionId);

    @Query("SELECT po FROM ProductOption po WHERE po.product.productId = :productId AND po.optionId = :optionId AND po.deletedAt IS NULL")
    Optional<ProductOption> findByProductIdAndOptionId(@Param("productId") Long productId, @Param("optionId") Long optionId);

    @Query("SELECT po FROM ProductOption po WHERE po.sku = :sku AND po.deletedAt IS NULL")
    Optional<ProductOption> findBySku(@Param("sku") String sku);

    boolean existsBySkuAndDeletedAtIsNull(String sku);
}

