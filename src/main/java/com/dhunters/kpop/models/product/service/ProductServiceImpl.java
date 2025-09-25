package com.dhunters.kpop.models.product.service;

import com.dhunters.kpop.models.member.repository.MemberRepository;
import com.dhunters.kpop.models.product.dto.registProduct.RegistProductReq;
import com.dhunters.kpop.models.product.dto.registProduct.RegistProductRes;
import com.dhunters.kpop.models.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	@Override
	public RegistProductRes registProduct(RegistProductReq request) {

		return null;
	}
}
