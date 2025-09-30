package com.dhunters.kpop.jwt.principal;

import java.security.Principal;

public record UserPrincipal(Long id, String role, Long version) implements Principal {
    @Override
    public String getName() {
        return String.valueOf(id);
    }
}