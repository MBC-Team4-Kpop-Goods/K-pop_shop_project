package com.dhunters.kpop.models.cart.controller;

import com.dhunters.kpop.core.api.v2.ApiResponse;
import com.dhunters.kpop.models.cart.dto.addProduct.AddCartProductReq;
import com.dhunters.kpop.models.cart.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@AllArgsConstructor
public class CartController {

	private final CartService cartService;

	/**
	 * 1. 장바구니에 상품 추가하기
	 * @param reqeust
	 */
	@PostMapping("/products")
	public ResponseEntity<?> addCartProduct(@RequestBody AddCartProductReq reqeust) {
		var res = cartService.addCartProduct(reqeust);
		return ApiResponse.created(res);
	}

	// 2. 장바구니 목록 조회

	// 3. 장바구니 상품 추가/빼기

	// 4. 장바구니 상품 삭제

	// 5. 장바구니 비우기

}
