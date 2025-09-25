package com.dhunters.kpop.models.product.repository;

import com.dhunters.kpop.common.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
