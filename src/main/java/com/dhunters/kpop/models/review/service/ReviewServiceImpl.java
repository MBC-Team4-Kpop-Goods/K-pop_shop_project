package com.dhunters.kpop.models.review.service;


import com.dhunters.kpop.common.entity.review.Review;
import com.dhunters.kpop.common.enums.ReviewStatus;
import com.dhunters.kpop.core.exception.NotFound;
import com.dhunters.kpop.models.review.dto.ReviewCreateReq;
import com.dhunters.kpop.models.review.dto.ReviewRes;
import com.dhunters.kpop.models.review.dto.ReviewUpdateReq;
import com.dhunters.kpop.models.review.mapper.ReviewMapper;
import com.dhunters.kpop.models.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

     private final ReviewMapper reviewMapper;
     private final ReviewRepository reviewRepository;

     @Override
     @Transactional
     public ReviewRes createReview(Long memberId, ReviewCreateReq req) {
         Review entity = reviewMapper.toEntity(req);
         entity.setMemberId(memberId);
         if(entity.getLikeCount() == null) entity.setLikeCount(0);
         if(entity.getStatus() == null )entity.setStatus(ReviewStatus.ACTIVE);
         return reviewMapper.toDto(reviewRepository.save(entity));

     }

    @Override
    @Transactional(readOnly = true)
    public ReviewRes getReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFound("review not found: " + reviewId));
        return reviewMapper.toDto(review);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Review> listReviews(Pageable pageable) {
        return null;
    }

    @Override
    @Transactional
    public ReviewRes updateReview(Long reviewId, ReviewUpdateReq request) {
         Review review = reviewRepository.findById(reviewId)
                 .orElseThrow(() -> new NotFound("review not found: " + reviewId));

         reviewMapper.updateEntity(review, request);

        // 필요시 값 보정 (예: likeCount 음수 방지 등)
        if (review.getLikeCount() != null && review.getLikeCount() < 0) {
            review.setLikeCount(0);
        }

        Review saved = reviewRepository.save(review);
        return reviewMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId) {
    Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new NotFound("review not found: " + reviewId));
    review.setStatus(ReviewStatus.DELETED);
    reviewRepository.save(review);

    }


}
