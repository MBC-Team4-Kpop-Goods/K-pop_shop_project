package com.dhunters.kpop.controller;

import com.dhunters.kpop.core.api.ApiResult;
import com.dhunters.kpop.dto.wishlist.WishListResponse;
import com.dhunters.kpop.common.entity.WishList;
import com.dhunters.kpop.service.WishListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishListController {

    private final WishListService wishListService;

    @GetMapping("/member/{memberId}")
    public ResponseEntity<ApiResult<Page<WishListResponse>>> getWishList(
            @PathVariable Long memberId,
            Pageable pageable) {

        Page<WishList> wishLists = wishListService.findWishListByMember(memberId, pageable);
        Page<WishListResponse> response = wishLists.map(this::convertToResponse);

        return ResponseEntity.ok(ApiResult.success(response));
    }

    @GetMapping("/member/{memberId}/all")
    public ResponseEntity<ApiResult<List<WishListResponse>>> getAllWishList(@PathVariable Long memberId) {
        List<WishList> wishLists = wishListService.findWishListByMember(memberId);
        List<WishListResponse> response = wishLists.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResult.success(response));
    }

    @GetMapping("/member/{memberId}/count")
    public ResponseEntity<ApiResult<Long>> getWishListCount(@PathVariable Long memberId) {
        Long count = wishListService.getWishListCount(memberId);
        return ResponseEntity.ok(ApiResult.success(count));
    }

    @GetMapping("/member/{memberId}/check/{productId}")
    public ResponseEntity<ApiResult<Boolean>> checkInWishList(
            @PathVariable Long memberId,
            @PathVariable Long productId) {

        boolean inWishList = wishListService.isInWishList(memberId, productId);
        return ResponseEntity.ok(ApiResult.success(inWishList));
    }

    @PostMapping("/member/{memberId}/product/{productId}")
    public ResponseEntity<ApiResult<Void>> addToWishList(
            @PathVariable Long memberId,
            @PathVariable Long productId) {

        wishListService.addToWishList(memberId, productId);
        return ResponseEntity.ok(ApiResult.success(null));
    }

    @DeleteMapping("/member/{memberId}/product/{productId}")
    public ResponseEntity<ApiResult<Void>> removeFromWishList(
            @PathVariable Long memberId,
            @PathVariable Long productId) {

        wishListService.removeFromWishList(memberId, productId);
        return ResponseEntity.ok(ApiResult.success(null));
    }

    @PostMapping("/member/{memberId}/product/{productId}/toggle")
    public ResponseEntity<ApiResult<Boolean>> toggleWishList(
            @PathVariable Long memberId,
            @PathVariable Long productId) {

        WishList result = wishListService.toggleWishList(memberId, productId);
        boolean added = result != null;

        return ResponseEntity.ok(ApiResult.success(added));
    }

    @DeleteMapping("/member/{memberId}")
    public ResponseEntity<ApiResult<Void>> clearWishList(@PathVariable Long memberId) {
        wishListService.clearWishList(memberId);
        return ResponseEntity.ok(ApiResult.success(null));
    }

    private WishListResponse convertToResponse(WishList wishList) {
        WishListResponse response = new WishListResponse();
        response.setMemberId(wishList.getMember().getMemberId());
        response.setProductId(wishList.getProduct().getProductId());
        response.setProductName(wishList.getProduct().getProductName());
        response.setPrice(wishList.getProduct().getPrice());
        response.setSalePrice(wishList.getProduct().getSalePrice());
        response.setProductStatus(wishList.getProduct().getProductStatus());
        response.setIsInStock(!"OUT_OF_STOCK".equals(wishList.getProduct().getProductStatus()));
        response.setAddedAt(wishList.getCreatedAt());

        return response;
    }
}
