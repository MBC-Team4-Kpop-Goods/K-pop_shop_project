package com.dhunters.kpop.service;

import com.dhunters.kpop.common.entity.Cart;
import com.dhunters.kpop.common.entity.CartItem;
import com.dhunters.kpop.common.entity.Member;
import com.dhunters.kpop.common.entity.Product;
import com.dhunters.kpop.common.entity.ProductOption;
import com.dhunters.kpop.repository.CartRepository;
import com.dhunters.kpop.repository.MemberRepository;
import com.dhunters.kpop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public Optional<Cart> findCartByMemberId(Long memberId) {
        return cartRepository.findActiveByMemberId(memberId);
    }

    public Optional<Cart> findCartBySessionId(String sessionId) {
        return cartRepository.findBySessionId(sessionId);
    }

    @Transactional
    public Cart createMemberCart(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Optional<Cart> existingCart = cartRepository.findActiveByMemberId(memberId);
        if (existingCart.isPresent()) {
            return existingCart.get();
        }

        Cart cart = new Cart();
        cart.setMember(member);
        cart.setCreatedAt(new Date());
        cart.setUpdatedAt(new Date());

        return cartRepository.save(cart);
    }

    @Transactional
    public Cart createGuestCart(String sessionId) {
        Optional<Cart> existingCart = cartRepository.findBySessionId(sessionId);
        if (existingCart.isPresent()) {
            return existingCart.get();
        }

        Cart cart = new Cart();
        cart.setSessionId(sessionId);
        cart.setCreatedAt(new Date());
        cart.setUpdatedAt(new Date());

        // 1시간 후 만료
        cart.setExpiresAt(new Date(System.currentTimeMillis() + 3600000));

        return cartRepository.save(cart);
    }

    @Transactional
    public CartItem addToCart(Long cartId, Long productId, Long optionId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 장바구니입니다."));

        Product product = productRepository.findActiveById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        // 기존 아이템이 있는지 확인
        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId) &&
                        (optionId == null ? item.getOption() == null :
                                item.getOption() != null && item.getOption().getOptionId().equals(optionId)))
                .findFirst();

        if (existingItem.isPresent()) {
            // 기존 아이템의 수량 증가
            CartItem item = existingItem.get();
            item.setQty(item.getQty() + quantity);
            item.setUpdatedAt(new Date());
            return item;
        } else {
            // 새 아이템 생성
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);

            if (optionId != null) {
                // ProductOption 조회 로직 필요 (ProductOptionRepository 추가 후)
                // cartItem.setOption(productOption);
            }

            cartItem.setQty(quantity);
            cartItem.setAddedAt(new Date());
            cartItem.setUpdatedAt(new Date());

            cart.getCartItems().add(cartItem);
            cart.setUpdatedAt(new Date());

            cartRepository.save(cart);
            return cartItem;
        }
    }

    @Transactional
    public void removeFromCart(Long cartId, Long cartItemId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 장바구니입니다."));

        cart.getCartItems().removeIf(item -> item.getCartItemId().equals(cartItemId));
        cart.setUpdatedAt(new Date());

        cartRepository.save(cart);
    }

    @Transactional
    public void clearCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 장바구니입니다."));

        cart.getCartItems().clear();
        cart.setUpdatedAt(new Date());

        cartRepository.save(cart);
    }

    @Transactional
    public void cleanupExpiredCarts() {
        cartRepository.findExpiredCarts(new Date())
                .forEach(cart -> cartRepository.delete(cart));
    }
}

