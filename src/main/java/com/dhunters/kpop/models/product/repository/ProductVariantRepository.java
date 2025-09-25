package com.dhunters.kpop.models.product.repository;

import com.dhunters.kpop.common.entity.product.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
}
