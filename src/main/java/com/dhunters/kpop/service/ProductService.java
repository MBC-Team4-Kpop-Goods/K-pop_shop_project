package com.dhunters.kpop.service;

import com.dhunters.kpop.common.entity.Product;
import com.dhunters.kpop.repository.ProductRepository;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.math.BigDecimal;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepository.findAllActive(pageable);
    }

    public Optional<Product> findById(Long productId) {
        return productRepository.findActiveById(productId);
    }

    public Page<Product> findByCategory(Long categoryId, Pageable pageable) {
        return productRepository.findActiveByCategoryId(categoryId, pageable);
    }

    public Page<Product> searchProducts(String keyword, Pageable pageable) {
        return productRepository.searchByKeyword(keyword, pageable);
    }

    public List<Product> findFeaturedProducts() {
        return productRepository.findFeaturedProducts();
    }

    public List<Product> findBestSellers(int limit) {
        return productRepository.findBestSellers(Pageable.ofSize(limit));
    }

    @Transactional
    public Product createProduct(Product product) {
        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long productId, Product updateInfo) {
        Product product = productRepository.findActiveById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        if (updateInfo.getProductName() != null) {
            product.setProductName(updateInfo.getProductName());
        }
        if (updateInfo.getDescription() != null) {
            product.setDescription(updateInfo.getDescription());
        }
        if (updateInfo.getPrice() != null) {
            product.setPrice(updateInfo.getPrice());
        }
        if (updateInfo.getSalePrice() != null) {
            product.setSalePrice(updateInfo.getSalePrice());
        }
        if (updateInfo.getProductStatus() != null) {
            product.setProductStatus(updateInfo.getProductStatus());
        }

        product.setUpdatedAt(new Date());
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findActiveById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        product.setDeletedAt(new Date());
        productRepository.save(product);
    }

    @Transactional
    public void increaseViewCount(Long productId) {
        Product product = productRepository.findActiveById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        product.setViewCount(product.getViewCount() + 1);
        product.setUpdatedAt(new Date());
        productRepository.save(product);
    }

    public Page<Product> findProductsWithFilters(Long categoryId, String keyword,
                                                 String minPrice, String maxPrice,
                                                 String brand, String status,
                                                 Pageable pageable) {

        Specification<Product> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 삭제되지 않은 상품만
            predicates.add(criteriaBuilder.isNull(root.get("deletedAt")));

            // 카테고리 필터
            if (categoryId != null) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("categoryId"), categoryId));
            }

            // 키워드 검색 (상품명, 설명, 브랜드)
            if (keyword != null && !keyword.trim().isEmpty()) {
                String likePattern = "%" + keyword.trim() + "%";
                Predicate nameMatch = criteriaBuilder.like(root.get("productName"), likePattern);
                Predicate descMatch = criteriaBuilder.like(root.get("description"), likePattern);
                Predicate brandMatch = criteriaBuilder.like(root.get("brand"), likePattern);
                predicates.add(criteriaBuilder.or(nameMatch, descMatch, brandMatch));
            }

            // 가격 범위 필터
            if (minPrice != null && !minPrice.trim().isEmpty()) {
                try {
                    BigDecimal min = new BigDecimal(minPrice);
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), min));
                } catch (NumberFormatException e) {
                    // 잘못된 가격 형식은 무시
                }
            }

            if (maxPrice != null && !maxPrice.trim().isEmpty()) {
                try {
                    BigDecimal max = new BigDecimal(maxPrice);
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), max));
                } catch (NumberFormatException e) {
                    // 잘못된 가격 형식은 무시
                }
            }

            // 브랜드 필터
            if (brand != null && !brand.trim().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("brand"), brand));
            }

            // 상품 상태 필터
            if (status != null && !status.trim().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("productStatus"), status));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return productRepository.findAll(spec, pageable);
    }
}
