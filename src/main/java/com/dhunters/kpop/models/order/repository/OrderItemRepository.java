package com.dhunters.kpop.models.order.repository;

import com.dhunters.kpop.common.entity.order.OrderItem;
import com.dhunters.kpop.common.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    /**
     * 특정 주문의 모든 아이템 조회
     */
    List<OrderItem> findByOrderInfoOrderIdOrderByOrderItemIdAsc(Long orderId);

    /**
     * 특정 상품 변형의 주문 아이템들 조회
     */
    List<OrderItem> findByVariantId(Long variantId);

    /**
     * 아이템 상태별 조회
     */
    List<OrderItem> findByStatus(OrderStatus.ItemStatus status);

    /**
     * 특정 주문의 특정 상태 아이템들 조회
     */
    List<OrderItem> findByOrderInfoOrderIdAndStatus(Long orderId, OrderStatus.ItemStatus status);
}


