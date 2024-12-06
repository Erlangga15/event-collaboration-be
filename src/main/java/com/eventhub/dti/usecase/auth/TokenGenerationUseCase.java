package com.eventhub.dti.usecase.auth;

import org.springframework.security.core.Authentication;

public interface TokenGenerationUseCase {
  String generateToken (Authentication authException);
}
