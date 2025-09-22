package com.dhunters.kpop.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

//JWT 임시설정용

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // 테스트용 임시 시큐리티 설정
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 구체 경로를 먼저
                        .requestMatchers(HttpMethod.POST, "/api/reviews").permitAll() // 예: 임시 개방
                        .requestMatchers("/health").permitAll()
                        // 그 다음 맨 마지막에 딱 한 번
                        .anyRequest().authenticated()
                )
                // Spring Security 6.1+ 방식
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {
                }));

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        String secret = "local-dev-secret-local-dev-secret"; // 로컬 전용 키
        var key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(key).build();
    }

}