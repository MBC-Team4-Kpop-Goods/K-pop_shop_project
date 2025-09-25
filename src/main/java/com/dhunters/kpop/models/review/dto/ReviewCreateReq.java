package com.dhunters.kpop.models.review.dto;

import com.dhunters.kpop.common.enums.ReviewStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class ReviewCreateReq {
    private Long productItemId;
    private Long orderItemId;
    private Integer rating;
    private String title;
    private String content;
    private String imagePaths;
    private Integer likeCount;
    private ReviewStatus status;
}
