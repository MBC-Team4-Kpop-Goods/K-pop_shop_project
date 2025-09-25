package com.dhunters.kpop.models.order.service;

import com.dhunters.kpop.models.order.dto.*;
import com.dhunters.kpop.common.entity.order.OrderInfo;
import com.dhunters.kpop.common.entity.order.OrderItem;
import com.dhunters.kpop.common.entity.order.Shipping;
import com.dhunters.kpop.common.entity.cart.Cart;
import com.dhunters.kpop.common.entity.cart.CartItem;
import com.dhunters.kpop.common.entity.Member;
import com.dhunters.kpop.common.enums.OrderStatus;
import com.dhunters.kpop.models.order.repository.OrderInfoRepository;
import com.dhunters.kpop.models.order.repository.OrderItemRepository;
import com.dhunters.kpop.models.order.repository.ShippingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrderInfoServiceImpl implements OrederInfoService {

    private final OrderInfoRepository orderInfoRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShippingRepository shippingRepository;

    // TODO: Member, ProductVariant Repository 구현 후 활성화
    // private final MemberRepository memberRepository;


    //장바구니에서 주문 생성
    @Override
    @Transactional
    public OrderDto createOrderFromCart(CreateOrderReqDto request) {
        log.info("Creating order from cart: {}", request.getCartId());

        return OrderDto.builder().build();
//        // 1. 장바구니 조회
//        Cart cart = cartRepository.findById(request.getCartId())
//                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
//
//        if (cart.getCartItems().isEmpty()) {
//            throw new IllegalArgumentException("Cart is empty");
//        }
//
//        // 2. 주문 번호 생성
//        String orderNumber = generateOrderNumber();
//
//        // 3. 총 상품 금액 계산
//        BigDecimal totalProductAmount = calculateTotalProductAmount(cart.getCartItems());
//
//        // 4. 최종 금액 계산 (상품금액 + 배송비 - 할인금액)
//        BigDecimal shippingFee = BigDecimal.valueOf(3000); // 임시 고정 배송비
//        BigDecimal discountAmount = request.getDiscountAmount() != null ?
//                request.getDiscountAmount() : BigDecimal.ZERO;
//        BigDecimal finalAmount = totalProductAmount.add(shippingFee).subtract(discountAmount);
//
//        // 5. 주문 정보 생성
//        // TODO: Member 구현 후 실제 객체로 변경
//        Member tempMember = new Member();
//        tempMember.setMemberId(1L); // 임시값
//
//        OrderInfo orderInfo = OrderInfo.builder()
//                .orderNumber(orderNumber)
//                .member(tempMember) // 임시
//                .ordererName(request.getOrdererName())
//                .ordererPhone(request.getOrdererPhone())
//                .ordererEmail(request.getOrdererEmail())
//                .shippingAddress(request.getShippingAddress())
//                .totalProductAmount(totalProductAmount)
//                .shippingFee(shippingFee)
//                .discountAmount(discountAmount)
//                .finalAmount(finalAmount)
//                .orderStatus(OrderStatus.Status.PENDING)
//                .build();
//
//        // 6. 주문 아이템 생성
//        for (CartItem cartItem : cart.getCartItems()) {
//            OrderItem orderItem = OrderItem.builder()
//                    .orderInfo(orderInfo)
//                    .variantId(1L) // 임시값 (ProductVariant 구현 후 변경)
//                    .productName("임시 상품명") // 임시값
//                    .variantInfo("임시 옵션") // 임시값
//                    .quantity(cartItem.getQuantity())
//                    .unitPrice(BigDecimal.valueOf(10000)) // 임시값
//                    .totalPrice(BigDecimal.valueOf(10000).multiply(BigDecimal.valueOf(cartItem.getQuantity())))
//                    .status(OrderStatus.ItemStatus.NORMAL)
//                    .build();
//
//            orderInfo.setOrderItems(List.of(orderItem));
//        }
//
//        // 7. 배송 정보 생성
//        Shipping shipping = Shipping.builder()
//                .orderInfo(orderInfo)
//                .shippingStatus(Shipping.ShippingStatus.PREPARING)
//                .orderMemo(request.getOrderMemo())
//                .build();
//
//        orderInfo.setShipping(shipping);
//
//        // 8. 주문 저장
//        OrderInfo savedOrder = orderInfoRepository.save(orderInfo);
//
//        // 9. 장바구니 비우기
//        cartService.clearCart(request.getCartId());
//
//        log.info("Order created successfully: {}", savedOrder.getOrderNumber());
//
//        return convertToOrderDto(savedOrder);
    }

    //회원의 주문 목록 조회
    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> getOrdersByMemberId(Long memberId) {
        log.info("Getting orders for member: {}", memberId);

        List<OrderInfo> orders = orderInfoRepository.findByMemberMemberIdOrderByCreatedAtDesc(memberId);

        return orders.stream()
                .map(this::convertToOrderDto)
                .collect(Collectors.toList());
    }

    //주문 상세 조회 (ID로)
    @Override
    @Transactional(readOnly = true)
    public OrderDto getOrderById(Long orderId) {
        log.info("Getting order by id: {}", orderId);

        OrderInfo orderInfo = orderInfoRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        return convertToOrderDto(orderInfo);
    }

    //주문 상세 조회 (주문번호로)
    @Override
    @Transactional(readOnly = true)
    public OrderDto getOrderByOrderNumber(String orderNumber) {
        log.info("Getting order by order number: {}", orderNumber);

        OrderInfo orderInfo = orderInfoRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        return convertToOrderDto(orderInfo);
    }

    //주문 상태 변경
    @Override
    @Transactional
    public OrderDto updateOrderStatus(Long orderId, OrderStatus.Status orderStatus) {
        log.info("Updating order status: orderId={}, status={}", orderId, orderStatus);

        OrderInfo orderInfo = orderInfoRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        orderInfo.setOrderStatus(orderStatus);
        OrderInfo savedOrder = orderInfoRepository.save(orderInfo);

        return convertToOrderDto(savedOrder);
    }

     //주문 취소
    @Override
    @Transactional
    public OrderDto cancelOrder(Long orderId) {
        log.info("Cancelling order: {}", orderId);

        OrderInfo orderInfo = orderInfoRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        // 취소 가능한 상태인지 확인
        if (orderInfo.getOrderStatus() == OrderStatus.Status.SHIPPED ||
                orderInfo.getOrderStatus() == OrderStatus.Status.DELIVERED) {
            throw new IllegalStateException("Cannot cancel order in current status");
        }

        orderInfo.setOrderStatus(OrderStatus.Status.CANCELLED);
        OrderInfo savedOrder = orderInfoRepository.save(orderInfo);

        return convertToOrderDto(savedOrder);
    }

   //배송 정보 등록/수정
    @Override
    @Transactional
    public OrderDto updateShippingInfo(Long orderId, ShippingDto shippingDto) {
        log.info("Updating shipping info for order: {}", orderId);

        OrderInfo orderInfo = orderInfoRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        Shipping shipping = orderInfo.getShipping();
        if (shipping == null) {
            shipping = new Shipping();
            shipping.setOrderInfo(orderInfo);
            orderInfo.setShipping(shipping);
        }

        shipping.setTrackingNumber(shippingDto.getTrackingNumber());
        shipping.setCourierCompany(shippingDto.getCourierCompany());
        shipping.setOrderMemo(shippingDto.getOrderMemo());

        OrderInfo savedOrder = orderInfoRepository.save(orderInfo);
        return convertToOrderDto(savedOrder);
    }

    //배송 상태 변경
    @Override
    @Transactional
    public OrderDto updateShippingStatus(Long orderId, Shipping.ShippingStatus shippingStatus) {
        log.info("Updating shipping status: orderId={}, status={}", orderId, shippingStatus);

        OrderInfo orderInfo = orderInfoRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        Shipping shipping = orderInfo.getShipping();
        if (shipping == null) {
            throw new IllegalArgumentException("Shipping info not found");
        }

        shipping.setShippingStatus(shippingStatus);

        // 배송 상태에 따라 주문 상태도 변경
        if (shippingStatus == Shipping.ShippingStatus.SHIPPED) {
            orderInfo.setOrderStatus(OrderStatus.Status.SHIPPED);
        } else if (shippingStatus == Shipping.ShippingStatus.DELIVERED) {
            orderInfo.setOrderStatus(OrderStatus.Status.DELIVERED);
        }

        OrderInfo savedOrder = orderInfoRepository.save(orderInfo);
        return convertToOrderDto(savedOrder);
    }

   //주문 아이템 상태 변경
    @Override
    @Transactional
    public OrderDto updateOrderItemStatus(Long orderItemId, OrderStatus.ItemStatus itemStatus) {
        log.info("Updating order item status: itemId={}, status={}", orderItemId, itemStatus);

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new IllegalArgumentException("Order item not found"));

        orderItem.setStatus(itemStatus);
        orderItemRepository.save(orderItem);

        return convertToOrderDto(orderItem.getOrderInfo());
    }

    //주문 상태별 조회
    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> getOrdersByStatus(OrderStatus.Status orderStatus) {
        log.info("Getting orders by status: {}", orderStatus);

        List<OrderInfo> orders = orderInfoRepository.findByOrderStatusOrderByCreatedAtDesc(orderStatus);

        return orders.stream()
                .map(this::convertToOrderDto)
                .collect(Collectors.toList());
    }

    //회원의 특정 상태 주문 조회
    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> getOrdersByMemberIdAndStatus(Long memberId, OrderStatus.Status orderStatus) {
        log.info("Getting orders by member and status: memberId={}, status={}", memberId, orderStatus);

        List<OrderInfo> orders = orderInfoRepository.findByMemberMemberIdAndOrderStatusOrderByCreatedAtDesc(
                memberId, orderStatus);

        return orders.stream()
                .map(this::convertToOrderDto)
                .collect(Collectors.toList());
    }

    //주문번호 생성 (예: ORD20250925001)
    private String generateOrderNumber() {
        LocalDateTime now = LocalDateTime.now();
        String dateStr = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String timeStr = now.format(DateTimeFormatter.ofPattern("HHmmss"));
        return "ORD" + dateStr + timeStr.substring(0, 3);
    }

    //장바구니 총 상품 금액 계산
    private BigDecimal calculateTotalProductAmount(List<CartItem> cartItems) {
        return null;
//        return cartItems.stream()
//                .map(item -> BigDecimal.valueOf(10000).multiply(BigDecimal.valueOf(item.getQuantity()))) // 임시 계산
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    //OrderInfo 엔티티를 OrderDto로 변환
    private OrderDto convertToOrderDto(OrderInfo orderInfo) {
        // OrderItem 변환
        List<OrderItemDto> orderItemDtos = orderInfo.getOrderItems().stream()
                .map(this::convertToOrderItemDto)
                .collect(Collectors.toList());

        // Shipping 변환
        ShippingDto shippingDto = orderInfo.getShipping() != null ?
                convertToShippingDto(orderInfo.getShipping()) : null;

        return OrderDto.builder()
                .orderId(orderInfo.getOrderId())
                .orderNumber(orderInfo.getOrderNumber())
                .memberId(1L) // 임시값
                .ordererName(orderInfo.getOrdererName())
                .ordererPhone(orderInfo.getOrdererPhone())
                .ordererEmail(orderInfo.getOrdererEmail())
                .shippingAddress(orderInfo.getShippingAddress())
                .totalProductAmount(orderInfo.getTotalProductAmount())
                .shippingFee(orderInfo.getShippingFee())
                .discountAmount(orderInfo.getDiscountAmount())
                .finalAmount(orderInfo.getFinalAmount())
                .orderStatus(orderInfo.getOrderStatus())
                .orderItems(orderItemDtos)
                .shipping(shippingDto)
                .createdAt(orderInfo.getCreatedAt())
                .build();
    }

    //OrderItem 엔티티를 OrderItemDto로 변환
    private OrderItemDto convertToOrderItemDto(OrderItem orderItem) {
        return OrderItemDto.builder()
                .orderItemId(orderItem.getOrderItemId())
                .variantId(orderItem.getVariantId())
                .productName(orderItem.getProductName())
                .variantInfo(orderItem.getVariantInfo())
                .optionInfo(orderItem.getOptionInfo())
                .quantity(orderItem.getQuantity())
                .unitPrice(orderItem.getUnitPrice())
                .totalPrice(orderItem.getTotalPrice())
                .status(orderItem.getStatus())
                .build();
    }

    //Shipping 엔티티를 ShippingDto로 변환
    private ShippingDto convertToShippingDto(Shipping shipping) {
        return ShippingDto.builder()
                .shippingId(shipping.getShippingId())
                .shippingStatus(shipping.getShippingStatus())
                .trackingNumber(shipping.getTrackingNumber())
                .courierCompany(shipping.getCourierCompany())
                .orderMemo(shipping.getOrderMemo())
                .build();
    }
}