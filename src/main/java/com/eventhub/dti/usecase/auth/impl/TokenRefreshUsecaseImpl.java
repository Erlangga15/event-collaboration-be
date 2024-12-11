package com.eventhub.dti.usecase.auth.impl;

import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import com.eventhub.dti.common.exceptions.DataNotFoundException;
import com.eventhub.dti.entity.User;
import com.eventhub.dti.infrastructure.dto.LoginResponseDTO;
import com.eventhub.dti.repository.UserRepository;
import com.eventhub.dti.usecase.auth.TokenGenerationUsecase;
import com.eventhub.dti.usecase.auth.TokenRefreshUsecase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenRefreshUsecaseImpl implements TokenRefreshUsecase {

    private final TokenGenerationUsecase tokenService;
    private final UserRepository userRepository;
    private final JwtDecoder jwtDecoder;

    @Override
    public LoginResponseDTO refreshTokenAndCreateResponse(String refreshToken) {
        String newAccessToken = tokenService.generateNewAccessToken(refreshToken);

        var jwt = jwtDecoder.decode(refreshToken);
        String email = jwt.getSubject();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        return LoginResponseDTO.fromUserAndTokens(user, newAccessToken, refreshToken);
    }
}
