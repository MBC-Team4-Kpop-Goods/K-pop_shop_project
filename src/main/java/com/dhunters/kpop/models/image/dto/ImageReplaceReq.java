package com.dhunters.kpop.models.image.dto;

import com.dhunters.kpop.common.enums.image.EntityType;
import com.dhunters.kpop.common.enums.image.RelationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageReplaceReq {

    // 기본 정보 수정
    private String altText;
    private Integer sortOrder;
    private Boolean isActive;

    // 관계 정보 수정 (선택적)
    private EntityType entityType;
    private Long entityId;
    private RelationType relationType;

}
