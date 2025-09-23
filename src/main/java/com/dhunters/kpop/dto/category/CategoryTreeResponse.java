package com.dhunters.kpop.dto.category;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CategoryTreeResponse {

    private Long categoryId;
    private String categoryName;
    private String categoryNameEn;
    private String slug;
    private String path;
    private Integer sortOrder;
    private Boolean isActive;
    private Date createdAt;
    private Date updatedAt;

    private List<CategoryTreeResponse> children;

    // 상품 개수 (선택적)
    private Long productCount;
}

