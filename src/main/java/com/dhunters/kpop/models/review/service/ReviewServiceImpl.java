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
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {

     private final ReviewMapper reviewMapper;
     private final ReviewRepository reviewRepository;

     @Override
     @Transactional
     public ReviewRes createReview(ReviewCreateReq req) {
         Review entity = reviewMapper.toEntity(req);

         if(entity.getLikeCount() == null) entity.setLikeCount(0);
         if(entity.getStatus() == null )entity.setStatus(ReviewStatus.ACTIVE);
         Review saved = reviewRepository.save(entity);
         return reviewMapper.toDto(saved);
     }

    @Override
    @Transactional(readOnly = true)
    public ReviewRes getReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFound("review not found: " + reviewId));
        return reviewMapper.toDto(review);
    }

    @Override
    public Page<Review> listReviews(Pageable pageable) {
        return null;
    }

    @Override
    public ReviewRes updateReview(Long reviewId, ReviewUpdateReq request) {
        return null;
    }

    @Override
    public void deleteReview(Long reviewId) {

    }


}
