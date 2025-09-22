package com.dhunters.kpop.models.review.repository;

import com.dhunters.kpop.common.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {


}
