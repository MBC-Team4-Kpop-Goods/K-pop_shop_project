package com.dhunters.kpop.common.config;

import java.util.List;

public class WhiteList {
    public static final List<String> URIS = List.of(
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**"
    );
}
