package com.dhunters.kpop.models.cart.repository;

import com.dhunters.kpop.common.entity.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
