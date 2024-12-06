package com.eventhub.dti.infrastructure.auth.controller;


import com.eventhub.dti.common.response.ApiResponse;
import com.eventhub.dti.infrastructure.auth.dto.LoginRequestDTO;
import com.eventhub.dti.infrastructure.auth.dto.LoginResponseDTO;
import com.eventhub.dti.usecase.auth.LoginUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
  private final LoginUseCase loginUsecase;

  public AuthController(LoginUseCase loginUsecase) {
    this.loginUsecase = loginUsecase;
  }

  @PostMapping
  public ResponseEntity<?> login(@Validated @RequestBody LoginRequestDTO req) {
    LoginResponseDTO response = loginUsecase.authenticateUser(req);
    return ApiResponse.successfulResponse("Login successful", response);
  }
}
