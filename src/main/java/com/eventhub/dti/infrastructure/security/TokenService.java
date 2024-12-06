package com.eventhub.dti.infrastructure.security;

import com.eventhub.dti.common.exception.DataNotFoundException;
import com.eventhub.dti.entity.User;
import com.eventhub.dti.infrastructure.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {
  private final JwtEncoder jwtEncoder;
  private final UserRepository userRepository;

  public TokenService(JwtEncoder jwtEncoder, UserRepository userRepository) {
    this.jwtEncoder = jwtEncoder;
    this.userRepository = userRepository;
  }

  public String generateToken(Authentication authentication) {
    Instant now = Instant.now();
    long expiry = 36000L;

    String email = authentication.getName();

    User user = userRepository.findByEmailContainsIgnoreCase(email).orElseThrow(() -> new DataNotFoundException("User not found"));

    String scope = authentication.getAuthorities().stream()
      .map(GrantedAuthority::getAuthority)
      .reduce((a, b) -> a + " " + b)
      .orElse("");

    JwtClaimsSet claims = JwtClaimsSet.builder()
      .issuer("self")
      .issuedAt(now)
      .expiresAt(now.plusSeconds(expiry))
      .subject(email)
      .claim("scope", scope)
      .claim("userId", user.getId())
      .build();

    return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }
}
