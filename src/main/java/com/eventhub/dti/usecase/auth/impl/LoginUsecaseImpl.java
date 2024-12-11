package com.eventhub.dti.usecase.auth.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.eventhub.dti.entity.User;
import com.eventhub.dti.entity.enums.TokenType;
import com.eventhub.dti.infrastructure.dto.LoginRequestDTO;
import com.eventhub.dti.infrastructure.dto.LoginResponseDTO;
import com.eventhub.dti.repository.UserRepository;
import com.eventhub.dti.usecase.auth.LoginUsecase;
import com.eventhub.dti.usecase.auth.TokenGenerationUsecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@Service
@RequiredArgsConstructor
public class LoginUsecaseImpl implements LoginUsecase {

    private final AuthenticationManager authenticationManager;
    private final TokenGenerationUsecase tokenService;
    private final UserRepository userRepository;

    @Override
    public LoginResponseDTO loginUser(LoginRequestDTO req) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));

            String accessToken = tokenService.generateToken(authentication, TokenType.ACCESS);
            String refreshToken = tokenService.generateToken(authentication, TokenType.REFRESH);

            User user = userRepository.findByEmail(req.getEmail())
                    .orElseThrow(() -> new AuthenticationException("User not found") {
                    });

            return LoginResponseDTO.fromUserAndTokens(user, accessToken, refreshToken);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }
}