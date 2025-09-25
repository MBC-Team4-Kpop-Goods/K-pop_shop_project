package com.dhunters.kpop.common.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@Table(name = "coupon")
public class Coupon extends BaseEntity{

    /* 쿠폰ID */
    @Id
    @Column(name = "coupon_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("couponId")
    private Long couponId;

    /* 쿠폰코드 */
    @Column(name = "coupon_code", length = 50)
    private String couponCode;

    /* 쿠폰명 */
    @Column(name = "coupon_name", length = 100)
    private String couponName;

    /* 쿠폰타입 */
    @Column(name = "coupon_type", length = 20)
    private String couponType;

    /* 할인값 */
    @Column(name = "discount_value")
    private Number discountValue;

    /* 최소주문 금액 */
    @Column(name = "min_order_amount")
    private Number minOrderAmount;

    /* 최대할인 금액 */
    @Column(name = "max_discount_amount")
    private Number maxDiscountAmount;

    /* 대상회원 등급 */
    @Column(name = "target_grade", length = 20)
    private String targetGrade;

    /* 사용 시작일 */
    @Column(name = "start_date")
    private Date startDate;

    /* 사용 종료일 */
    @Column(name = "end_date")
    private Date endDate;

    /* 사용제한 횟수 */
    @Column(name = "usage_limit")
    private Number usageLimit;

    /* 개인 사용제한 */
    @Column(name = "personal_limit")
    private Number personalLimit;

    /* 사용여부 */
    @Column(name = "is_active")
    private Boolean isActive;

//    /* 등록일시 */
//    @Column(name = "created_at")
//    private Date createdAt;

}
