package com.dhunters.kpop.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//JWT 임시설정용
@Configuration
public class AuthConfig {
    @Bean
    public CurrentUserProvider currentUserProvider() {
        return () -> 1L; // 테스트용
    }
}