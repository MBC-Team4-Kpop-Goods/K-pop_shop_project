package com.dhunters.kpop.jwt.auth.service;

import com.dhunters.kpop.common.enums.Role;
import com.dhunters.kpop.core.api.v2.servlet.ClientInfo;
import com.dhunters.kpop.jwt.JwtIssuer;
import com.dhunters.kpop.jwt.JwtVerifier;
import com.dhunters.kpop.jwt.auth.dto.performLogin.PerformLoginReq;
import com.dhunters.kpop.jwt.auth.dto.performLogin.PerformLoginRes;
import com.dhunters.kpop.jwt.auth.dto.refreshToken.RefreshTokenReq;
import com.dhunters.kpop.jwt.auth.dto.refreshToken.RefreshTokenRes;
import com.dhunters.kpop.jwt.models.userRefreshToken.domain.UserRefreshToken;
import com.dhunters.kpop.jwt.models.userRefreshToken.repository.UserRefreshTokenRepository;
import com.dhunters.kpop.jwt.service.RefreshTokenHasher;
import com.dhunters.kpop.jwt.service.TokenGuard;
import com.dhunters.kpop.models.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final StringRedisTemplate redisTemplate;

    private final JwtIssuer jwtIssuer;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenHasher refreshTokenHasher;

    private final MemberRepository memberRepository;
    private final UserRefreshTokenRepository userRefreshTokensRepository;
    private final TokenGuard tokenGuard;
    private final JwtVerifier jwtVerifier;

    @Override
    @Transactional
    public PerformLoginRes performLogin(PerformLoginReq req, ClientInfo clientInfo) {

        var member = memberRepository.findByLoginId(req.getLoginId())
                .orElseThrow(() -> new BadCredentialsException("invalid credentials"));

        // 1. 비밀번호 검증
        if (!passwordEncoder.matches(req.getLoginPw(), member.getPasswordHash()))
            throw new BadCredentialsException("invalid credentials");

        // 2. token 버전 ++ (잠금)
        var ver = member.getTokenVersion();
        int updated = memberRepository.bumpVersionIfMatch(member.getMemberId(), ver);
        if (updated != 1)
            throw new CredentialsExpiredException("stale token"); // 동시 갱신 차단

        // 3. 신규 토큰 생성
        var newVer = ver + 1;
        var accessToken = jwtIssuer.issueAccessToken(member.getMemberId(), member.getRole().toString(), newVer);
        var refreshToken = jwtIssuer.issueRefreshToken(member.getMemberId(), newVer);

        var claims = jwtVerifier.getClaims(refreshToken);
        String jti = claims.getId();
        String tokenHash = refreshTokenHasher.hash(refreshToken);
        LocalDateTime expiresAt = claims.getExpiration()
                .toInstant()
                .atZone(ZoneId.of("UTC"))
                .toLocalDateTime();

        UserRefreshToken newToken = UserRefreshToken.createToken(
                member, jti, tokenHash, expiresAt,
                clientInfo.getDeviceId(), clientInfo.getUserAgent(), clientInfo.getIp()
        );

        // refresh token 저장
        userRefreshTokensRepository.save(newToken);

        String redisKey = "k-pop:member:" + member.getMemberId() + ":ver"; // 캐시 DB 토큰 value KEY
        redisTemplate.opsForValue().set(redisKey,
                Long.toString(newVer),
                java.time.Duration.ofMinutes(15));

        return PerformLoginRes.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public RefreshTokenRes refreshToken(RefreshTokenReq req, ClientInfo clientInfo) {
        String refreshToken = req.getRefreshToken();
        var claims = jwtVerifier.getClaims(refreshToken);

        // refresh token 검증 ( aud, exp )
        tokenGuard.verifyRefresh(claims);


        // 1. token 분해
        var memberId = Long.parseLong(claims.getSubject());
        var ver = claims.get("version", Long.class);
        var jti = claims.getId(); // UUID


        // 2. 무결성 검증? + DB Lock
        var oldRefreshToken = userRefreshTokensRepository.findByJtiForUpdate(jti)
                .orElseThrow(() -> new BadCredentialsException("invalid credentials"));

        // --- 멤버 검증
        if (!oldRefreshToken.getMember().getMemberId().equals(memberId))
            throw new BadCredentialsException("unauthorized");

        // --- 교체된 jti 검증
        if (oldRefreshToken.getReplacedDate() != null)
            throw new BadCredentialsException("revoked");

        // --- 토큰 Hash 검증
        if (!refreshTokenHasher.matches(refreshToken, oldRefreshToken.getTokenHash()))
            throw new BadCredentialsException("mismatch");


        // 3. token 버전 ++ (잠금)
        int updated = memberRepository.bumpVersionIfMatch(memberId, ver);
        if (updated != 1)
            throw new CredentialsExpiredException("stale token"); // 동시 갱신 차단


        // 4. 신규 토큰 생성
        // 버전은 변수에서 처리
        Long newVer = ver + 1;

        // 권한은 권한만 조회,  rotate 를 위한 member 참조는 reference 로 호출한다. (select 쿼리 호출되지 않음)
        Role role = memberRepository.findRoleByAccountId(memberId);
        if (role == null)
            throw new BadCredentialsException("unauthorized");

        var memberRef = memberRepository.getReferenceById(memberId);

        var newAccessToken = jwtIssuer.issueAccessToken(memberId, role.toString(), newVer);
        var newRefreshToken = jwtIssuer.issueRefreshToken(memberId, newVer);

        var newClaims = jwtVerifier.getClaims(newRefreshToken);
        String newJti = newClaims.getId();
        String tokenHash = refreshTokenHasher.hash(refreshToken);
        LocalDateTime expiresAt = newClaims.getExpiration()
                .toInstant()
                .atZone(ZoneId.of("UTC"))
                .toLocalDateTime();

        UserRefreshToken newToken = UserRefreshToken.createToken(
                memberRef, newJti, tokenHash, expiresAt,
                clientInfo.getDeviceId(), clientInfo.getUserAgent(), clientInfo.getIp()
        );


        // 신규 refresh token 저장
        userRefreshTokensRepository.save(newToken);

        String redisKey = "k-pop:member:" + memberId + ":ver"; // 캐시 DB 토큰 value KEY
        redisTemplate.opsForValue().set(redisKey,
                Long.toString(newVer),
                java.time.Duration.ofMinutes(15));


        // 5. 기존 refresh token rotate
        oldRefreshToken.rotate(newClaims.getId());

        return RefreshTokenRes.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}
