package com.dhunters.kpop.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryCreateRequest {

    @NotBlank(message = "카테고리명은 필수입니다.")
    private String categoryName;

    private String categoryNameEn;

    @NotBlank(message = "슬러그는 필수입니다.")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "슬러그는 소문자, 숫자, 하이픈만 가능합니다.")
    private String slug;

    private Long parentId;

    private Integer sortOrder = 0;

    private Boolean isActive = true;
}

