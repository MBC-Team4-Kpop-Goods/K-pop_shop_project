package com.dhunters.kpop.models.review.repository;

import com.dhunters.kpop.common.entity.review.Review;
import com.dhunters.kpop.common.enums.ReviewStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByStatusNot(ReviewStatus status, Pageable pageable);

}
