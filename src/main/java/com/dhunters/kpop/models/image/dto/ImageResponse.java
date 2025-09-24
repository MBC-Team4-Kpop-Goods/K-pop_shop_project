package com.dhunters.kpop.models.image.dto;


import com.dhunters.kpop.common.enums.image.EntityType;
import com.dhunters.kpop.common.enums.image.RelationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageResponse {

    private Long imageId;

    private Long memberId;

    private String originalFilename;

    private String storedFilename;

    private String filePath;

    private String fileUrl;  // 접근 가능한 URL

    private Long fileSize;

    private String mimeType;

    private Integer width;

    private Integer height;

    private String altText;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
    
    
    // 이미지 메타 데이터
    private EntityType entityType;
    private Long entityId;
    private RelationType relationType;
    private Integer sortOrder;
    private Boolean isActive;

}
