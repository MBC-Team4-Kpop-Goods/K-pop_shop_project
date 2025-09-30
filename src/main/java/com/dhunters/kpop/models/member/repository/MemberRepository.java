package com.dhunters.kpop.models.member.repository;

import com.dhunters.kpop.common.entity.Member;
import com.dhunters.kpop.common.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String email);

    /**
     * 토큰 버전 조회
     *
     * @param id
     * @return
     */
    @Query("select m.tokenVersion from Member m where m.memberId = :id")
    Long findTokenVersionById(@Param("id") Long id);

    /**
     * 토큰 버전 원자적 증가
     *
     * @param id
     * @param cur
     * @return
     */
    @Modifying
    @Query("update Member m set m.tokenVersion = m.tokenVersion + 1 where m.memberId = :id and m.tokenVersion = :cur")
    int bumpVersionIfMatch(@Param("id") Long id, @Param("cur") Long cur);

    /**
     * 멤버 권한 조회
     *
     * @param id
     * @return
     */
    @Query("select m.role from Member m where m.memberId = :id")
    Role findRoleByAccountId(@Param("id") Long id);
}
