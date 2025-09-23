package com.dhunters.kpop.repository;

import com.dhunters.kpop.common.entity.WishList;
import com.dhunters.kpop.common.entity.WishlistId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList, WishlistId> {

    @Query("SELECT w FROM WishList w WHERE w.member.memberId = :memberId")
    Page<WishList> findByMemberId(@Param("memberId") Long memberId, Pageable pageable);

    @Query("SELECT w FROM WishList w WHERE w.member.memberId = :memberId")
    List<WishList> findByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT COUNT(w) FROM WishList w WHERE w.member.memberId = :memberId")
    Long countByMemberId(@Param("memberId") Long memberId);

    boolean existsByMemberMemberIdAndProductProductId(Long memberId, Long productId);
}

