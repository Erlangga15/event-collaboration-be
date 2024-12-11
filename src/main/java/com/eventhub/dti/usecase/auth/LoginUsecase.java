package com.eventhub.dti.usecase.auth;

import com.eventhub.dti.infrastructure.dto.LoginRequestDTO;
import com.eventhub.dti.infrastructure.dto.LoginResponseDTO;

public interface LoginUsecase {
    LoginResponseDTO loginUser(LoginRequestDTO request);
}