package com.dhunters.kpop.models.product.service;

import com.dhunters.kpop.models.product.dto.registProduct.RegistProductReq;
import com.dhunters.kpop.models.product.dto.registProduct.RegistProductRes;

public interface ProductService {
	RegistProductRes registProduct(RegistProductReq request);
}
