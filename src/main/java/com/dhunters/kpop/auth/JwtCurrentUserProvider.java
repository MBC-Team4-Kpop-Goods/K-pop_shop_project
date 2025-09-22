package com.dhunters.kpop.auth;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
//JWT 임시설정용

@Component
@Profile({"prod","stage"})
public class JwtCurrentUserProvider implements CurrentUserProvider {
    @Override
    public Long getMemberId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof Jwt jwt)) return null;
        Long memberId = jwt.getClaim("memberId");
        if (memberId != null) return memberId;
        String sub = jwt.getSubject();
        return (sub != null) ? Long.valueOf(sub) : null; // 팀 합의에 맞춰 변경 가능
    }
}