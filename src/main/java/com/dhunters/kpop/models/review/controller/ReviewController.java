package com.dhunters.kpop.models.review.controller;

import com.dhunters.kpop.auth.CurrentUserProvider;
import com.dhunters.kpop.core.api.ApiResult;
import com.dhunters.kpop.models.review.dto.ReviewCreateReq;
import com.dhunters.kpop.models.review.dto.ReviewRes;
import com.dhunters.kpop.models.review.dto.ReviewUpdateReq;
import com.dhunters.kpop.models.review.service.ReviewService;
import com.nimbusds.jwt.JWT;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final CurrentUserProvider currentUser;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<ReviewRes> createReview(@RequestBody ReviewCreateReq reviewCreateReq) {
        Long memberId = currentUser.getMemberId();
        if (memberId == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "login required");
        //reviewCreateReq.setMemberId(memberId); // 서비스가 이 값만 신뢰
        return new ApiResult<>(reviewService.createReview(memberId, reviewCreateReq));
    }

    @GetMapping("/{reviewId}")
    public ApiResult<ReviewRes> get(@PathVariable Long reviewId)  {
        return new ApiResult<>(reviewService.getReview(reviewId));
    }

    @PatchMapping(value = "/{reviewId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<ReviewRes> update(@PathVariable Long reviewId,
                                       @RequestBody ReviewUpdateReq reviewUpdateReq) {
        return new ApiResult<>(reviewService.updateReview(reviewId, reviewUpdateReq));
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
    }
}
