package com.dhunters.kpop.jwt.auth.dto.refreshToken;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenRes {
	String accessToken;
	String refreshToken;
}
