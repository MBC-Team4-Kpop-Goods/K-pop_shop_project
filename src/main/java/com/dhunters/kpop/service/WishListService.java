package com.dhunters.kpop.service;

import com.dhunters.kpop.common.entity.Member;
import com.dhunters.kpop.common.entity.Product;
import com.dhunters.kpop.common.entity.WishList;
import com.dhunters.kpop.common.entity.WishlistId;
import com.dhunters.kpop.repository.MemberRepository;
import com.dhunters.kpop.repository.ProductRepository;
import com.dhunters.kpop.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishListService {

    private final WishListRepository wishListRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public Page<WishList> findWishListByMember(Long memberId, Pageable pageable) {
        return wishListRepository.findByMemberId(memberId, pageable);
    }

    public List<WishList> findWishListByMember(Long memberId) {
        return wishListRepository.findByMemberId(memberId);
    }

    public Long getWishListCount(Long memberId) {
        return wishListRepository.countByMemberId(memberId);
    }

    public boolean isInWishList(Long memberId, Long productId) {
        return wishListRepository.existsByMemberMemberIdAndProductProductId(memberId, productId);
    }

    @Transactional
    public WishList addToWishList(Long memberId, Long productId) {
        if (isInWishList(memberId, productId)) {
            throw new IllegalArgumentException("이미 위시리스트에 있는 상품입니다.");
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Product product = productRepository.findActiveById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        WishlistId wishlistId = new WishlistId(memberId, productId);

        WishList wishList = new WishList();
        wishList.setId(wishlistId);
        wishList.setMember(member);
        wishList.setProduct(product);
        wishList.setCreatedAt(new Date());

        return wishListRepository.save(wishList);
    }

    @Transactional
    public void removeFromWishList(Long memberId, Long productId) {
        WishlistId wishlistId = new WishlistId(memberId, productId);

        if (!wishListRepository.existsById(wishlistId)) {
            throw new IllegalArgumentException("위시리스트에 없는 상품입니다.");
        }

        wishListRepository.deleteById(wishlistId);
    }

    @Transactional
    public void clearWishList(Long memberId) {
        List<WishList> wishLists = wishListRepository.findByMemberId(memberId);
        wishListRepository.deleteAll(wishLists);
    }

    @Transactional
    public WishList toggleWishList(Long memberId, Long productId) {
        if (isInWishList(memberId, productId)) {
            removeFromWishList(memberId, productId);
            return null;
        } else {
            return addToWishList(memberId, productId);
        }
    }
}

