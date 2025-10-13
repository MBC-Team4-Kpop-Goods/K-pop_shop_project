package com.dhunters.kpop.models.coupon.controller;

import com.dhunters.kpop.models.coupon.dto.deleteCoupon.DeleteCouponReq;
import com.dhunters.kpop.models.coupon.dto.deleteCoupon.DeleteCouponRes;
import com.dhunters.kpop.models.coupon.dto.getCouponDetail.GetCouponDetailRes;
import com.dhunters.kpop.models.coupon.dto.getCouponList.GetCouponListReq;
import com.dhunters.kpop.models.coupon.dto.getCouponList.GetCouponListRes;
import com.dhunters.kpop.models.coupon.dto.modifyCoupon.ModifyCouponReq;
import com.dhunters.kpop.models.coupon.dto.modifyCoupon.ModifyCouponRes;
import com.dhunters.kpop.models.coupon.dto.postCoupon.PostCouponReq;
import com.dhunters.kpop.models.coupon.dto.postCoupon.PostCouponRes;
import com.dhunters.kpop.models.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    // C: 쿠폰 생성
    @PostMapping("/coupons")
    public ResponseEntity<PostCouponRes> create(@RequestBody(required = false) PostCouponReq req) {
        return ResponseEntity.ok(new PostCouponRes()); // 200 OK + 빈 DTO
    }

    // R: 쿠폰 전체 목록
    @GetMapping("/coupons")
    public ResponseEntity<GetCouponListRes> list(GetCouponListReq req) {
        return ResponseEntity.ok(new GetCouponListRes());
    }

    // R: 쿠폰 단건 조회
    @GetMapping("/coupons/{id}")
    public ResponseEntity<GetCouponDetailRes> detail(@PathVariable Long id) {
        return ResponseEntity.ok(new GetCouponDetailRes());
    }

    // U: 쿠폰 수정
    @PutMapping("/coupons/{id}")
    public ResponseEntity<ModifyCouponRes> update(@PathVariable Long id,
                                                  @RequestBody(required = false) ModifyCouponReq req) {
        return ResponseEntity.ok(new ModifyCouponRes());
    }

    // D: 쿠폰 삭제
    @DeleteMapping("/coupons/{id}")
    public ResponseEntity<DeleteCouponRes> delete(@PathVariable Long id,
                                                  @RequestBody(required = false) DeleteCouponReq req) {
        return ResponseEntity.ok(new DeleteCouponRes());
    }
}
