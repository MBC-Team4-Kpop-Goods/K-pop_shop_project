package com.dhunters.kpop.repository;

import com.dhunters.kpop.common.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.deletedAt IS NULL AND c.isActive = true")
    List<Category> findAllActive();

    @Query("SELECT c FROM Category c WHERE c.parent IS NULL AND c.deletedAt IS NULL AND c.isActive = true ORDER BY c.sortOrder")
    List<Category> findRootCategories();

    @Query("SELECT c FROM Category c WHERE c.parent.categoryId = :parentId AND c.deletedAt IS NULL AND c.isActive = true ORDER BY c.sortOrder")
    List<Category> findByParentId(@Param("parentId") Long parentId);

    @Query("SELECT c FROM Category c WHERE c.slug = :slug AND c.deletedAt IS NULL AND c.isActive = true")
    Optional<Category> findBySlug(@Param("slug") String slug);

    @Query("SELECT c FROM Category c WHERE c.categoryId = :categoryId AND c.deletedAt IS NULL")
    Optional<Category> findActiveById(@Param("categoryId") Long categoryId);
}

