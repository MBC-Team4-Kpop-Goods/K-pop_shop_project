package com.dhunters.kpop.repository;

import com.dhunters.kpop.common.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("SELECT p FROM Product p WHERE p.deletedAt IS NULL")
    Page<Product> findAllActive(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.productId = :productId AND p.deletedAt IS NULL")
    Optional<Product> findActiveById(@Param("productId") Long productId);

    @Query("SELECT p FROM Product p WHERE p.category.categoryId = :categoryId AND p.deletedAt IS NULL")
    Page<Product> findActiveByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.productName LIKE %:keyword% OR p.description LIKE %:keyword% AND p.deletedAt IS NULL")
    Page<Product> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.isFeatured = true AND p.deletedAt IS NULL")
    List<Product> findFeaturedProducts();

    @Query("SELECT p FROM Product p WHERE p.productStatus = 'ACTIVE' AND p.deletedAt IS NULL ORDER BY p.salesCount DESC")
    List<Product> findBestSellers(Pageable pageable);
}
