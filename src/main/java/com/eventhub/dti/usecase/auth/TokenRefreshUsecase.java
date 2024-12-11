package com.eventhub.dti.usecase.auth;

import com.eventhub.dti.infrastructure.dto.LoginResponseDTO;

public interface TokenRefreshUsecase {
    LoginResponseDTO refreshTokenAndCreateResponse(String refreshToken);
}
