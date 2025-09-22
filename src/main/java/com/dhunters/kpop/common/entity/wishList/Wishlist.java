package com.dhunters.kpop.common.entity.wishList;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "wishlist")
@Getter @Setter
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_id")
    private Long wishlistId;

    // 외래키 제약 없음: Long 값만 보유
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "product_item_id")
    private Long productItemId;

}
