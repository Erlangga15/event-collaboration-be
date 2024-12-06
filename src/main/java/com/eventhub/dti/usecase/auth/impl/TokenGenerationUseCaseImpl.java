package com.eventhub.dti.usecase.auth.impl;

import com.eventhub.dti.common.exception.DataNotFoundException;
import com.eventhub.dti.entity.User;
import com.eventhub.dti.infrastructure.user.repository.UserRepository;
import com.eventhub.dti.usecase.auth.TokenGenerationUseCase;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenGenerationUseCaseImpl implements TokenGenerationUseCase {
  private final JwtEncoder jwtEncoder;
  private final UserRepository userRepository;

  public TokenGenerationUseCaseImpl(JwtEncoder jwtEncoder, UserRepository usersRepository) {
    this.jwtEncoder = jwtEncoder;
    this.userRepository = usersRepository;
  }

  public String generateToken(Authentication authentication) {
    Instant now = Instant.now();
    long expiry = 36000L;

    String email = authentication.getName();

    User user = userRepository.findByEmailContainsIgnoreCase(email)
      .orElseThrow(() -> new DataNotFoundException("User not found"));

    String scope = authentication.getAuthorities().stream()
      .map(GrantedAuthority::getAuthority)
      .reduce((a, b) -> a + " " + b)
      .orElse("");

    JwtClaimsSet claims = JwtClaimsSet.builder()
      .issuedAt(now)
      .expiresAt(now.plusSeconds(expiry))
      .subject(email)
      .claim("scope", scope)
      .claim("userId", user.getId())
      .build();

    JwsHeader jwsHeader = JwsHeader.with(() -> "HS256").build();
    return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
  }
}
