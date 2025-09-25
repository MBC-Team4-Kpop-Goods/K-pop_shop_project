package com.dhunters.kpop.models.order.repository;

import com.dhunters.kpop.common.entity.order.OrderInfo;
import com.dhunters.kpop.common.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long> {


   // 회원 ID로 주문 목록 조회 (최신순)
   List<OrderInfo> findByMemberMemberIdOrderByCreatedAtDesc(Long memberId);


    // 주문 번호로 주문 조회
    Optional<OrderInfo> findByOrderNumber(String orderNumber);


    //주문 상태별 주문 목록 조회
    List<OrderInfo> findByOrderStatusOrderByCreatedAtDesc(OrderStatus.Status orderStatus);

  // 회원 ID와 주문 상태로 주문 목록 조회
  List<OrderInfo> findByMemberMemberIdAndOrderStatusOrderByCreatedAtDesc(
            Long memberId, OrderStatus.Status orderStatus);

    // TODO: Member, ProductVariant 구현 후 활성화
    /**
     * 주문과 주문 아이템들을 함께 조회 (N+1 문제 해결)
     */
    // @Query("SELECT o FROM OrderInfo o LEFT JOIN FETCH o.orderItems oi " +
    //        "LEFT JOIN FETCH o.shipping " +
    //        "WHERE o.orderId = :orderId")
    // Optional<OrderInfo> findOrderWithItemsAndShippingById(@Param("orderId") Long orderId);

    /**
     * 회원의 주문과 아이템들 함께 조회
     */
    // @Query("SELECT o FROM OrderInfo o LEFT JOIN FETCH o.orderItems oi " +
    //        "LEFT JOIN FETCH o.shipping " +
    //        "WHERE o.member.memberId = :memberId " +
    //        "ORDER BY o.createdAt DESC")
    // List<OrderInfo> findOrdersWithItemsByMemberId(@Param("memberId") Long memberId);
}
