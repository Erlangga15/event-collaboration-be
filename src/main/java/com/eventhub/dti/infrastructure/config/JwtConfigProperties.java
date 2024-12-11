package com.eventhub.dti.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfigProperties {
    private String secret;
    private Long expiration;
    private Long refreshTokenExpiration;

    @PostConstruct
    public void validateConfig() {
        if (secret == null || secret.isEmpty()) {
            log.error("JWT secret is not configured");
        }
        if (expiration == null || expiration <= 0) {
            log.error("Invalid JWT expiration time");
        }
        if (refreshTokenExpiration == null || refreshTokenExpiration <= 0) {
            log.error("Invalid JWT refresh token expiration time");
        }
    }
}