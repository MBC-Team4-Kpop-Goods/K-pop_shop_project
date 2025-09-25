package com.dhunters.kpop.models.cart.service;

import com.dhunters.kpop.models.cart.dto.addProduct.AddCartProductReq;
import com.dhunters.kpop.models.cart.dto.addProduct.AddCartProductRes;

public interface CartService {

	AddCartProductRes addCartProduct(AddCartProductReq request);
}
