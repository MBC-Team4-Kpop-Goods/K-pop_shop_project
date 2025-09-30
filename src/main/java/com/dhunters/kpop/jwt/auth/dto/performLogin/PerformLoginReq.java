package com.dhunters.kpop.jwt.auth.dto.performLogin;

import lombok.Data;

@Data
public class PerformLoginReq {
	private String loginId;
	private String loginPw;
}
