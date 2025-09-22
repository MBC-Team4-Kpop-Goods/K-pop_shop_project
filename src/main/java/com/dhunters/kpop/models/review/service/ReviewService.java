package com.dhunters.kpop.models.review.service;

import com.dhunters.kpop.common.entity.review.Review;
import com.dhunters.kpop.models.review.dto.ReviewCreateReq;
import com.dhunters.kpop.models.review.dto.ReviewRes;
import com.dhunters.kpop.models.review.dto.ReviewUpdateReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    ReviewRes createReview(ReviewCreateReq request);

    ReviewRes getReview(Long reviewId);

    Page<Review> listReviews(Pageable pageable);

    ReviewRes updateReview(Long reviewId, ReviewUpdateReq request);

    void deleteReview(Long reviewId);

}
