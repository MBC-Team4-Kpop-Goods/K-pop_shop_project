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
public class ImageUploadReq {
    
    private Long memberId;
    
    private EntityType entityType;
    
    private Long entityId;
    
    private RelationType relationType;
    
    private String altText;
    
    @Builder.Default
    private Integer sortOrder = 0;
    
    // 추후 not null 조건 추적 필요
}
