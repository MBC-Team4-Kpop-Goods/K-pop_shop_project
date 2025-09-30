package com.dhunters.kpop.jwt.auth.service;

import com.dhunters.kpop.core.api.v2.servlet.ClientInfo;
import com.dhunters.kpop.jwt.auth.dto.performLogin.PerformLoginReq;
import com.dhunters.kpop.jwt.auth.dto.performLogin.PerformLoginRes;
import com.dhunters.kpop.jwt.auth.dto.refreshToken.RefreshTokenReq;
import com.dhunters.kpop.jwt.auth.dto.refreshToken.RefreshTokenRes;

public interface AuthService {
    PerformLoginRes performLogin(PerformLoginReq req, ClientInfo clientInfo);
    RefreshTokenRes refreshToken(RefreshTokenReq req, ClientInfo clientInfo);
}
