package com.eventhub.dti.usecase.auth.impl;

import com.eventhub.dti.common.exception.DataNotFoundException;
import com.eventhub.dti.infrastructure.auth.dto.LoginRequestDTO;
import com.eventhub.dti.infrastructure.auth.dto.LoginResponseDTO;
import com.eventhub.dti.usecase.auth.LoginUseCase;
import com.eventhub.dti.usecase.auth.TokenGenerationUseCase;
import lombok.extern.java.Log;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Log
@Service
public class LoginUseCaseImpl implements LoginUseCase {
  private final AuthenticationManager authenticationManager;
  private final TokenGenerationUseCase tokenService;

  public LoginUseCaseImpl(AuthenticationManager authenticationManager, TokenGenerationUseCase tokenService) {
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
  }

  @Override
  public LoginResponseDTO authenticateUser(LoginRequestDTO req) {
    try {
      log.info("Loggingin with");
      log.info(req.getEmail());
      log.info(req.getPassword());
      Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
      );
      String token = tokenService.generateToken(authentication);
      return new LoginResponseDTO(token);
    } catch (AuthenticationException e) {
      throw new DataNotFoundException("Wrong credentials");
    }
  }
}
