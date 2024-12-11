package com.eventhub.dti.infrastructure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventhub.dti.common.exceptions.TokenException;
import com.eventhub.dti.common.response.Response;
import com.eventhub.dti.infrastructure.dto.LoginRequestDTO;
import com.eventhub.dti.infrastructure.dto.LoginResponseDTO;
import com.eventhub.dti.infrastructure.dto.LogoutRequestDTO;
import com.eventhub.dti.infrastructure.dto.TokenRefreshRequestDTO;
import com.eventhub.dti.infrastructure.security.Claims;
import com.eventhub.dti.usecase.auth.LoginUsecase;
import com.eventhub.dti.usecase.auth.LogoutUsecase;
import com.eventhub.dti.usecase.auth.TokenRefreshUsecase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginUsecase loginUsecase;
    private final LogoutUsecase logoutUsecase;
    private final TokenRefreshUsecase tokenRefreshUsecase;

    @PostMapping("/login")
    public ResponseEntity<Response<LoginResponseDTO>> login(@Valid @RequestBody LoginRequestDTO req) {
        return Response.successfulResponse("Login successful", loginUsecase.loginUser(req));
    }

    @PostMapping("/logout")
    public ResponseEntity<Response<Boolean>> logout(@Valid @RequestBody LogoutRequestDTO req) {
        String accessToken = Claims.getJwtTokenString();
        if (accessToken == null) {
            throw new IllegalStateException("JWT not found in SecurityContext");
        }
        req.setAccessToken(accessToken);
        return Response.successfulResponse("Logout successful", logoutUsecase.logoutUser(req));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Response<LoginResponseDTO>> refresh(@Valid @RequestBody TokenRefreshRequestDTO req) {
        try {
            return Response.successfulResponse("Token refresh successful",
                    tokenRefreshUsecase.refreshTokenAndCreateResponse(req.getRefreshToken()));
        } catch (Exception e) {
            throw new TokenException("Failed to refresh token: " + e.getMessage());
        }
    }
}
