package com.dhunters.kpop.models.product.service;

import com.dhunters.kpop.models.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	public void registProduct() {

	}

}
