package com.dhunters.kpop.models.product.repository;

import com.dhunters.kpop.common.entity.product.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Long> {
}
