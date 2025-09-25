package com.dhunters.kpop.models.cart.service;

import com.dhunters.kpop.models.cart.dto.addProduct.AddCartProductReq;
import com.dhunters.kpop.models.cart.dto.addProduct.AddCartProductRes;
import com.dhunters.kpop.models.cart.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

	private final CartRepository cartRepository;

	@Override
	public AddCartProductRes addCartProduct(AddCartProductReq request) {
		return null;
	}
}
