package com.dhunters.kpop.models.product.controller;

import com.dhunters.kpop.core.api.v2.ApiResponse;
import com.dhunters.kpop.models.cart.dto.addProduct.AddCartProductReq;
import com.dhunters.kpop.models.product.dto.registProduct.RegistProductReq;
import com.dhunters.kpop.models.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

	private final ProductService productService;

	/**
	 * 1. 장바구니에 상품 추가하기
	 *
	 * @param reqeust
	 */
	@PostMapping("/products")
	public ResponseEntity<?> registProduct(@RequestBody RegistProductReq reqeust) {
		var res = productService.registProduct(reqeust);
		return ApiResponse.created(res);
	}


}
