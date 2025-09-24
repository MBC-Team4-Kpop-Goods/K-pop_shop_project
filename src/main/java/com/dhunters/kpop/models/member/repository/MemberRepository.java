package com.dhunters.kpop.models.member.repository;

import com.dhunters.kpop.common.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {


}
