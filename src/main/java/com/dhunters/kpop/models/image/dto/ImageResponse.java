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
    private EntityType entityType;        // 어떤 도메인인지 (product, member ...)
    private Long entityId;               // 어떤 엔티티에 속하는지 '1' (상품 ID 1)
    private RelationType relationType;  // 어떤 용도로 사용하는지 (프로필, 배너, ...)
    private Integer sortOrder;          // 여러 이미지 정렬 순서 정의
    private Boolean isActive;           // 활성 상태 정의

}
