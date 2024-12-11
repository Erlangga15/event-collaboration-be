package com.eventhub.dti.usecase.auth;

import org.springframework.security.core.Authentication;

import com.eventhub.dti.entity.enums.TokenType;

public interface TokenGenerationUsecase {
    String generateToken(Authentication authentication, TokenType tokenType);

    String generateNewAccessToken(String refreshToken);
}