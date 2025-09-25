package com.dhunters.kpop.models.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dhunters.kpop.common.entity.cart.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
