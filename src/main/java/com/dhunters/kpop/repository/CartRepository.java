package com.dhunters.kpop.repository;

import com.dhunters.kpop.common.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByMemberMemberId(Long memberId);

    Optional<Cart> findBySessionId(String sessionId);

    @Query("SELECT c FROM Cart c WHERE c.expiresAt < :now")
    List<Cart> findExpiredCarts(@Param("now") Date now);

    @Query("SELECT c FROM Cart c WHERE c.member.memberId = :memberId AND c.deletedAt IS NULL")
    Optional<Cart> findActiveByMemberId(@Param("memberId") Long memberId);
}

