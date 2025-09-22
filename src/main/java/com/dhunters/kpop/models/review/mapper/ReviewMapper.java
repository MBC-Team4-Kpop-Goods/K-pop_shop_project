package com.dhunters.kpop.models.review.mapper;

import com.dhunters.kpop.common.entity.review.Review;
import com.dhunters.kpop.models.review.dto.ReviewCreateReq;
import com.dhunters.kpop.models.review.dto.ReviewRes;
import com.dhunters.kpop.models.review.dto.ReviewUpdateReq;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE // 남는 필드 경고 무시(선호)
)
public interface ReviewMapper {

    //엔티티->응답dto
    ReviewRes toDto(Review review);

    // 생성요청 dto -> 엔티티
    // LikeCount/status가 null이면 서비스에서 기본값 보정하므로 여기선 그대로 매핑
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    Review toEntity(ReviewCreateReq request);

    //부분업데이트 : null값은 무시(덮어쓰지않음)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Review review, ReviewUpdateReq request);
}
