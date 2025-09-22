package com.dhunters.kpop.common.entity.review;

import com.dhunters.kpop.common.entity.BaseEntity;
import com.dhunters.kpop.common.enums.ReviewStatus;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "review")
@Getter @Setter
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "product_item_id")
    private Long productItemId;

    @Column(name = "order_item_id")
    private Long orderItemId;

    @Column(name = "rating")
    private Integer rating; // 1~5 검증은 서비스/DTO에서

    @Column(name = "title", length = 200)
    private String title;

    @Lob
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "image_paths")
    private String imagePaths; // ["https://...","/img/..."]

    @Column(name = "like_count")
    private Integer likeCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private ReviewStatus status; // ACTIVE/HIDDEN/DELETED


}
