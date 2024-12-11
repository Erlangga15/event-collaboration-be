package com.eventhub.dti.usecase.auth.impl;

import java.time.Instant;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.eventhub.dti.common.exceptions.DataNotFoundException;
import com.eventhub.dti.entity.User;
import com.eventhub.dti.entity.enums.TokenType;
import com.eventhub.dti.repository.UserRepository;
import com.eventhub.dti.usecase.auth.TokenGenerationUsecase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenGenerationUsecaseImpl implements TokenGenerationUsecase {
    private static final long ACCESS_TOKEN_EXPIRY = 900L;
    private static final long REFRESH_TOKEN_EXPIRY = 86400L;
    private static final String CLAIM_SCOPE = "scope";
    private static final String CLAIM_USER_ID = "userId";
    private static final String CLAIM_TYPE = "type";

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final UserRepository usersRepository;

    @Override
    public String generateToken(Authentication authentication, TokenType tokenType) {
        Instant now = Instant.now();
        long expiry = (tokenType == TokenType.ACCESS) ? ACCESS_TOKEN_EXPIRY : REFRESH_TOKEN_EXPIRY;

        String email = authentication.getName();
        User user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .reduce((a, b) -> a + " " + b)
                .orElse("");

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(email)
                .claim(CLAIM_SCOPE, scope)
                .claim(CLAIM_USER_ID, user.getId())
                .claim(CLAIM_TYPE, tokenType.name())
                .build();

        JwsHeader jwsHeader = JwsHeader.with(() -> "HS256").build();
        String token = jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();

        try {
            jwtDecoder.decode(token);
        } catch (Exception e) {
            throw new RuntimeException("Token validation failed", e);
        }

        return token;
    }

    @Override
    public String generateNewAccessToken(String refreshToken) {
        var jwt = jwtDecoder.decode(refreshToken);
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(now.plusSeconds(ACCESS_TOKEN_EXPIRY))
                .subject(jwt.getSubject())
                .claim(CLAIM_SCOPE, jwt.getClaimAsString(CLAIM_SCOPE))
                .claim(CLAIM_USER_ID, jwt.getClaimAsString(CLAIM_USER_ID))
                .claim(CLAIM_TYPE, TokenType.ACCESS.name())
                .build();

        JwsHeader jwsHeader = JwsHeader.with(() -> "HS256").build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }
}