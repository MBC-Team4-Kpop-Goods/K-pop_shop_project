package com.dhunters.kpop.controller;

import com.dhunters.kpop.core.api.ApiResult;
import com.dhunters.kpop.common.entity.Category;
import com.dhunters.kpop.dto.category.CategoryCreateRequest;
import com.dhunters.kpop.dto.category.CategoryTreeResponse;
import com.dhunters.kpop.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResult<List<CategoryTreeResponse>>> getCategoryTree() {
        List<CategoryTreeResponse> categoryTree = categoryService.getCategoryTree();
        return ResponseEntity.ok(ApiResult.success(categoryTree));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResult<Category>> getCategory(@PathVariable Long categoryId) {
        Category category = categoryService.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        return ResponseEntity.ok(ApiResult.success(category));
    }

    @PostMapping
    public ResponseEntity<ApiResult<Category>> createCategory(@Valid @RequestBody CategoryCreateRequest request) {
        Category category = convertToEntity(request);
        Category savedCategory = categoryService.createCategory(category);

        return ResponseEntity.ok(ApiResult.success(savedCategory));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResult<Category>> updateCategory(
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryCreateRequest request) {

        Category updateInfo = convertToEntity(request);
        Category updatedCategory = categoryService.updateCategory(categoryId, updateInfo);

        return ResponseEntity.ok(ApiResult.success(updatedCategory));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResult<Void>> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(ApiResult.success(null));
    }

    @GetMapping("/root")
    public ResponseEntity<ApiResult<List<Category>>> getRootCategories() {
        List<Category> categories = categoryService.findRootCategories();
        return ResponseEntity.ok(ApiResult.success(categories));
    }

    @GetMapping("/{categoryId}/children")
    public ResponseEntity<ApiResult<List<Category>>> getSubCategories(@PathVariable Long categoryId) {
        List<Category> subCategories = categoryService.findSubCategories(categoryId);
        return ResponseEntity.ok(ApiResult.success(subCategories));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ApiResult<Category>> getCategoryBySlug(@PathVariable String slug) {
        Category category = categoryService.findBySlug(slug)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        return ResponseEntity.ok(ApiResult.success(category));
    }

    private Category convertToEntity(CategoryCreateRequest request) {
        Category category = new Category();
        category.setCategoryName(request.getCategoryName());
        category.setCategoryNameEn(request.getCategoryNameEn());
        category.setSlug(request.getSlug());
        category.setSortOrder(request.getSortOrder());
        category.setIsActive(request.getIsActive());

        if (request.getParentId() != null) {
            Category parent = categoryService.findById(request.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 부모 카테고리입니다."));
            category.setParent(parent);
        }

        return category;
    }
}
