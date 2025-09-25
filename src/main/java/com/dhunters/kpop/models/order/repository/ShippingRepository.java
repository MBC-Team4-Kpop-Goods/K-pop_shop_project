package com.dhunters.kpop.models.order.repository;

import com.dhunters.kpop.common.entity.order.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShippingRepository extends JpaRepository<Shipping, Integer> {


    /**
     * 주문 ID로 배송 정보 조회
     */
    Optional<Shipping> findByOrderInfoOrderId(Long orderId);

    /**
     * 운송장 번호로 배송 정보 조회
     */
    Optional<Shipping> findByTrackingNumber(String trackingNumber);

    /**
     * 택배사별 배송 목록 조회
     */
    List<Shipping> findByCourierCompany(String courierCompany);

    /**
     * 배송 상태별 조회
     */
    List<Shipping> findByShippingStatus(Shipping.ShippingStatus shippingStatus);



}
