package com.dhunters.kpop.models.coupon.repository;

import com.dhunters.kpop.common.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
