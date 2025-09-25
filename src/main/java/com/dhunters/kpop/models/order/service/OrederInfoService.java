package com.dhunters.kpop.models.order.service;

import com.dhunters.kpop.common.entity.order.Shipping;
import com.dhunters.kpop.common.enums.OrderStatus;
import com.dhunters.kpop.models.order.dto.CreateOrderReqDto;
import com.dhunters.kpop.models.order.dto.OrderDto;
import com.dhunters.kpop.models.order.dto.ShippingDto;

import java.util.List;

//주문 관련 모든 비즈니스 로직을 정의 (OrderInfo + OrderItem + Shipping)
public interface OrederInfoService {

    /**
     * 1. 장바구니에서 주문 생성
     * - 장바구니 내용을 주문으로 변환
     * - 주문번호 자동 생성
     * - 금액 계산 및 검증
     * @param request 주문 생성 요청 정보
     * @return 생성된 주문 정보
     */
    OrderDto createOrderFromCart(CreateOrderReqDto request);

    /**
     * 2. 회원의 주문 목록 조회 (최신순)
     * - 로그인한 회원의 모든 주문 조회
     * @param memberId 회원 ID
     * @return 주문 목록
     */
    List<OrderDto> getOrdersByMemberId(Long memberId);

    /**
     * 3. 주문 상세 조회 (주문 ID로)
     * - 특정 주문의 상세 정보 조회
     * - 주문 아이템, 배송 정보 포함
     * @param orderId 주문 ID
     * @return 주문 상세 정보
     */
    OrderDto getOrderById(Long orderId);

    /**
     * 4. 주문 상세 조회 (주문 번호로)
     * - 주문번호로 주문 조회 (고객 문의 시 사용)
     * @param orderNumber 주문번호
     * @return 주문 상세 정보
     */
    OrderDto getOrderByOrderNumber(String orderNumber);

    /**
     * 5. 주문 상태 변경
     * - 주문 진행 상태 업데이트 (관리자용)
     * - PENDING → CONFIRMED → SHIPPED → DELIVERED
     * @param orderId 주문 ID
     * @param orderStatus 변경할 주문 상태
     * @return 업데이트된 주문 정보
     */
    OrderDto updateOrderStatus(Long orderId, OrderStatus.Status orderStatus);

    /**
     * 6. 주문 취소
     * - 주문 상태를 CANCELLED로 변경
     * - 취소 가능한 상태인지 검증
     * @param orderId 주문 ID
     * @return 취소된 주문 정보
     */
    OrderDto cancelOrder(Long orderId);

    /**
     * 7. 배송 정보 등록/수정
     * - 운송장 번호, 택배사 정보 등록
     * - 배송 상태 업데이트
     * @param orderId 주문 ID
     * @param shippingDto 배송 정보
     * @return 업데이트된 주문 정보 (배송 정보 포함)
     */
    OrderDto updateShippingInfo(Long orderId, ShippingDto shippingDto);

    /**
     * 8. 배송 상태 변경
     * - 배송 진행 상태 업데이트
     * - PREPARING → SHIPPED → IN_TRANSIT → DELIVERED
     * @param orderId 주문 ID
     * @param shippingStatus 변경할 배송 상태
     * @return 업데이트된 주문 정보
     */
    OrderDto updateShippingStatus(Long orderId, Shipping.ShippingStatus shippingStatus);

    /**
     * 9. 주문 아이템 상태 변경 (개별)
     * - 특정 아이템만 취소/반품 처리
     * - 부분 취소/반품 시 사용
     * @param orderItemId 주문 아이템 ID
     * @param itemStatus 변경할 아이템 상태
     * @return 업데이트된 주문 정보
     */
    OrderDto updateOrderItemStatus(Long orderItemId, OrderStatus.ItemStatus itemStatus);

    /**
     * 10. 주문 상태별 조회 (관리자용)
     * - 특정 상태의 모든 주문 조회
     * @param orderStatus 주문 상태
     * @return 해당 상태의 주문 목록
     */
    List<OrderDto> getOrdersByStatus(OrderStatus.Status orderStatus);

    /**
     * 11. 회원의 특정 상태 주문 조회
     * - 회원의 특정 상태 주문만 필터링
     * @param memberId 회원 ID
     * @param orderStatus 주문 상태
     * @return 필터링된 주문 목록
     */
    List<OrderDto> getOrdersByMemberIdAndStatus(Long memberId, OrderStatus.Status orderStatus);

 }