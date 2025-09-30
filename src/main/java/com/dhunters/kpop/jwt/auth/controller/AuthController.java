package com.dhunters.kpop.jwt.auth.controller;

import com.dhunters.kpop.core.api.v2.ApiResponse;
import com.dhunters.kpop.core.api.v2.servlet.ClientInfo;
import com.dhunters.kpop.jwt.auth.dto.performLogin.PerformLoginReq;
import com.dhunters.kpop.jwt.auth.dto.refreshToken.RefreshTokenReq;
import com.dhunters.kpop.jwt.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/auth/v1"})
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> performLogin(@RequestBody PerformLoginReq request,
                                          HttpServletRequest httpRequest) {

        var clientInfo = new ClientInfo(httpRequest);
        var res = authService.performLogin(request, clientInfo);

        return ApiResponse.ok(res);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenReq request,
                                          HttpServletRequest httpRequest) {

        // RTR (Refresh Token Rotation)
        var clientInfo = new ClientInfo(httpRequest);
        var res = authService.refreshToken(request, clientInfo);

        return ApiResponse.ok(res);
    }
}