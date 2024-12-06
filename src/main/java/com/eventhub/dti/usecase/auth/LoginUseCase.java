package com.eventhub.dti.usecase.auth;

import com.eventhub.dti.infrastructure.auth.dto.LoginRequestDTO;
import com.eventhub.dti.infrastructure.auth.dto.LoginResponseDTO;

public interface LoginUseCase {
  LoginResponseDTO authenticateUser(LoginRequestDTO req);
}
