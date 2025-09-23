package com.dhunters.kpop.service;

import com.dhunters.kpop.common.entity.Category;
import com.dhunters.kpop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findAllActiveCategories() {
        return categoryRepository.findAllActive();
    }

    public List<Category> findRootCategories() {
        return categoryRepository.findRootCategories();
    }

    public List<Category> findSubCategories(Long parentId) {
        return categoryRepository.findByParentId(parentId);
    }

    public Optional<Category> findById(Long categoryId) {
        return categoryRepository.findActiveById(categoryId);
    }

    public Optional<Category> findBySlug(String slug) {
        return categoryRepository.findBySlug(slug);
    }

    @Transactional
    public Category createCategory(Category category) {
        category.setCreatedAt(new Date());
        category.setUpdatedAt(new Date());
        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(Long categoryId, Category updateInfo) {
        Category category = categoryRepository.findActiveById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        if (updateInfo.getCategoryName() != null) {
            category.setCategoryName(updateInfo.getCategoryName());
        }
        if (updateInfo.getCategoryNameEn() != null) {
            category.setCategoryNameEn(updateInfo.getCategoryNameEn());
        }
        if (updateInfo.getSlug() != null) {
            category.setSlug(updateInfo.getSlug());
        }
        if (updateInfo.getSortOrder() != null) {
            category.setSortOrder(updateInfo.getSortOrder());
        }
        if (updateInfo.getIsActive() != null) {
            category.setIsActive(updateInfo.getIsActive());
        }

        category.setUpdatedAt(new Date());
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findActiveById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        category.setDeletedAt(new Date());
        categoryRepository.save(category);
    }

    public List<Category> getCategoryHierarchy(Long categoryId) {
        // 카테고리 계층구조를 반환하는 메서드 (추후 구현 가능)
        return List.of();
    }

    public List<CategoryTreeResponse> getCategoryTree() {
        List<Category> rootCategories = findRootCategories();
        return rootCategories.stream()
                .map(this::buildCategoryTree)
                .collect(java.util.stream.Collectors.toList());
    }

    private CategoryTreeResponse buildCategoryTree(Category category) {
        CategoryTreeResponse response = new CategoryTreeResponse();
        response.setCategoryId(category.getCategoryId());
        response.setCategoryName(category.getCategoryName());
        response.setCategoryNameEn(category.getCategoryNameEn());
        response.setSlug(category.getSlug());
        response.setPath(category.getPath());
        response.setSortOrder(category.getSortOrder());
        response.setIsActive(category.getIsActive());
        response.setCreatedAt(category.getCreatedAt());
        response.setUpdatedAt(category.getUpdatedAt());

        // 자식 카테고리들 재귀적으로 구성
        if (category.getChildren() != null && !category.getChildren().isEmpty()) {
            List<CategoryTreeResponse> children = category.getChildren().stream()
                    .filter(child -> child.getIsActive() && child.getDeletedAt() == null)
                    .sorted((a, b) -> a.getSortOrder().compareTo(b.getSortOrder()))
                    .map(this::buildCategoryTree)
                    .collect(java.util.stream.Collectors.toList());
            response.setChildren(children);
        }

        return response;
    }
}
