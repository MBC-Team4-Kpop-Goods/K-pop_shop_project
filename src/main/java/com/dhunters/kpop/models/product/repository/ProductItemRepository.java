package com.dhunters.kpop.models.product.repository;

import com.dhunters.kpop.common.entity.product.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {
}
