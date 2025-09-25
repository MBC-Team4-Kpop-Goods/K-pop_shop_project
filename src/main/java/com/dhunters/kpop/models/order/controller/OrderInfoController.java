package com.dhunters.kpop.models.order.controller;

import com.dhunters.kpop.core.api.ApiResult;
import com.dhunters.kpop.models.order.dto.CreateOrderReqDto;
import com.dhunters.kpop.models.order.dto.OrderDto;
import com.dhunters.kpop.models.order.dto.ShippingDto;
import com.dhunters.kpop.models.order.service.OrederInfoService;
import com.dhunters.kpop.common.enums.OrderStatus;
import com.dhunters.kpop.common.entity.order.Shipping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderInfoController {

    private final OrederInfoService orderInfoService;

    /**
     * 1. 장바구니에서 주문 생성
     * POST /api/orders
     */
    @PostMapping
    public ResponseEntity<ApiResult<OrderDto>> createOrderFromCart(@RequestBody CreateOrderReqDto request) {
        log.info("POST /api/orders - Creating order from cartId={}", request.getCartId());

        OrderDto response = orderInfoService.createOrderFromCart(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResult<>(response));
    }

    /**
     * 2. 회원의 주문 목록 조회
     * GET /api/orders/member/{memberId}
     */
    @GetMapping("/member/{memberId}")
    public ResponseEntity<ApiResult<List<OrderDto>>> getOrdersByMemberId(@PathVariable Long memberId) {
        log.info("GET /api/orders/member/{} - Getting orders", memberId);

        List<OrderDto> response = orderInfoService.getOrdersByMemberId(memberId);
        return ResponseEntity.ok(new ApiResult<>(response));
    }

    /**
     * 3. 주문 상세 조회 (ID로)
     * GET /api/orders/{orderId}
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResult<OrderDto>> getOrderById(@PathVariable Long orderId) {
        log.info("GET /api/orders/{} - Getting order detail", orderId);

        OrderDto response = orderInfoService.getOrderById(orderId);
        return ResponseEntity.ok(new ApiResult<>(response));
    }

    /**
     * 4. 주문 상세 조회 (주문번호로)
     * GET /api/orders/number/{orderNumber}
     */
    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<ApiResult<OrderDto>> getOrderByOrderNumber(@PathVariable String orderNumber) {
        log.info("GET /api/orders/number/{} - Getting order detail", orderNumber);

        OrderDto response = orderInfoService.getOrderByOrderNumber(orderNumber);
        return ResponseEntity.ok(new ApiResult<>(response));
    }

    /**
     * 5. 주문 상태 변경
     * PUT /api/orders/{orderId}/status
     */
    @PutMapping("/{orderId}/status")
    public ResponseEntity<ApiResult<OrderDto>> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus.Status status) {
        log.info("PUT /api/orders/{}/status - Updating status={}", orderId, status);

        OrderDto response = orderInfoService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(new ApiResult<>(response));
    }

    /**
     * 6. 주문 취소
     * PUT /api/orders/{orderId}/cancel
     */
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResult<OrderDto>> cancelOrder(@PathVariable Long orderId) {
        log.info("PUT /api/orders/{}/cancel - Cancelling order", orderId);

        OrderDto response = orderInfoService.cancelOrder(orderId);
        return ResponseEntity.ok(new ApiResult<>(response));
    }

    /**
     * 7. 배송 정보 등록/수정
     * PUT /api/orders/{orderId}/shipping
     */
    @PutMapping("/{orderId}/shipping")
    public ResponseEntity<ApiResult<OrderDto>> updateShippingInfo(
            @PathVariable Long orderId,
            @RequestBody ShippingDto shippingDto) {
        log.info("PUT /api/orders/{}/shipping - Updating shipping info", orderId);

        OrderDto response = orderInfoService.updateShippingInfo(orderId, shippingDto);
        return ResponseEntity.ok(new ApiResult<>(response));
    }

    /**
     * 8. 배송 상태 변경
     * PUT /api/orders/{orderId}/shipping/status
     */
    @PutMapping("/{orderId}/shipping/status")
    public ResponseEntity<ApiResult<OrderDto>> updateShippingStatus(
            @PathVariable Long orderId,
            @RequestParam Shipping.ShippingStatus status) {
        log.info("PUT /api/orders/{}/shipping/status - Updating status={}", orderId, status);

        OrderDto response = orderInfoService.updateShippingStatus(orderId, status);
        return ResponseEntity.ok(new ApiResult<>(response));
    }

    /**
     * 9. 주문 아이템 상태 변경
     * PUT /api/orders/items/{orderItemId}/status
     */
    @PutMapping("/items/{orderItemId}/status")
    public ResponseEntity<ApiResult<OrderDto>> updateOrderItemStatus(
            @PathVariable Long orderItemId,
            @RequestParam OrderStatus.ItemStatus status) {
        log.info("PUT /api/orders/items/{}/status - Updating item status={}", orderItemId, status);

        OrderDto response = orderInfoService.updateOrderItemStatus(orderItemId, status);
        return ResponseEntity.ok(new ApiResult<>(response));
    }

    /**
     * 10. 주문 상태별 조회
     * GET /api/orders/status/{status}
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResult<List<OrderDto>>> getOrdersByStatus(@PathVariable OrderStatus.Status status) {
        log.info("GET /api/orders/status/{} - Getting orders", status);

        List<OrderDto> response = orderInfoService.getOrdersByStatus(status);
        return ResponseEntity.ok(new ApiResult<>(response));
    }

    /**
     * 11. 회원의 특정 상태 주문 조회
     * GET /api/orders/member/{memberId}/status/{status}
     */
    @GetMapping("/member/{memberId}/status/{status}")
    public ResponseEntity<ApiResult<List<OrderDto>>> getOrdersByMemberIdAndStatus(
            @PathVariable Long memberId,
            @PathVariable OrderStatus.Status status) {
        log.info("GET /api/orders/member/{}/status/{} - Getting orders", memberId, status);

        List<OrderDto> response = orderInfoService.getOrdersByMemberIdAndStatus(memberId, status);
        return ResponseEntity.ok(new ApiResult<>(response));
    }
}
