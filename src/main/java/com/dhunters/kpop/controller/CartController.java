package com.dhunters.kpop.controller;

import com.dhunters.kpop.core.api.ApiResult;
import com.dhunters.kpop.dto.cart.AddToCartRequest;
import com.dhunters.kpop.dto.cart.CartResponse;
import com.dhunters.kpop.common.entity.Cart;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<ApiResult<CartResponse>> getCart(
            @RequestParam(required = false) Long memberId,
            HttpSession session) {

        Cart cart;
        if (memberId != null) {
            // 회원 장바구니
            cart = cartService.findCartByMemberId(memberId)
                    .orElseGet(() -> cartService.createMemberCart(memberId));
        } else {
            // 게스트 장바구니
            String sessionId = session.getId();
            cart = cartService.findCartBySessionId(sessionId)
                    .orElseGet(() -> cartService.createGuestCart(sessionId));
        }

        CartResponse response = convertToResponse(cart);
        return ResponseEntity.ok(ApiResult.success(response));
    }

    @PostMapping
    public ResponseEntity<ApiResult<CartResponse>> createCart(
            @RequestParam(required = false) Long memberId,
            HttpSession session) {

        Cart cart;
        if (memberId != null) {
            cart = cartService.createMemberCart(memberId);
        } else {
            String sessionId = session.getId();
            cart = cartService.createGuestCart(sessionId);
        }

        CartResponse response = convertToResponse(cart);
        return ResponseEntity.ok(ApiResult.success(response));
    }

    @PostMapping("/member/{memberId}/items")
    public ResponseEntity<ApiResult<Void>> addToMemberCart(
            @PathVariable Long memberId,
            @Valid @RequestBody AddToCartRequest request) {

        Cart cart = cartService.findCartByMemberId(memberId)
                .orElseGet(() -> cartService.createMemberCart(memberId));

        cartService.addToCart(cart.getCartId(), request.getProductId(),
                request.getOptionId(), request.getQuantity());

        return ResponseEntity.ok(ApiResult.success(null));
    }

    @PostMapping("/guest/items")
    public ResponseEntity<ApiResult<Void>> addToGuestCart(
            @Valid @RequestBody AddToCartRequest request,
            HttpSession session) {

        String sessionId = session.getId();
        Cart cart = cartService.findCartBySessionId(sessionId)
                .orElseGet(() -> cartService.createGuestCart(sessionId));

        cartService.addToCart(cart.getCartId(), request.getProductId(),
                request.getOptionId(), request.getQuantity());

        return ResponseEntity.ok(ApiResult.success(null));
    }

    @DeleteMapping("/{cartId}/items/{cartItemId}")
    public ResponseEntity<ApiResult<Void>> removeFromCart(
            @PathVariable Long cartId,
            @PathVariable Long cartItemId) {

        cartService.removeFromCart(cartId, cartItemId);
        return ResponseEntity.ok(ApiResult.success(null));
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<ApiResult<Void>> clearCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.ok(ApiResult.success(null));
    }

    private CartResponse convertToResponse(Cart cart) {
        CartResponse response = new CartResponse();
        response.setCartId(cart.getCartId());
        response.setMemberId(cart.getMember() != null ? cart.getMember().getMemberId() : null);
        response.setSessionId(cart.getSessionId());
        response.setExpiresAt(cart.getExpiresAt());
        response.setCreatedAt(cart.getCreatedAt());
        response.setUpdatedAt(cart.getUpdatedAt());

        // 장바구니 아이템들과 총액 계산은 추후 구현
        response.setTotalItems(cart.getCartItems() != null ? cart.getCartItems().size() : 0);

        return response;
    }
}
