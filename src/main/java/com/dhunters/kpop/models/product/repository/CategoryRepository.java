package com.dhunters.kpop.models.product.repository;

import com.dhunters.kpop.common.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
