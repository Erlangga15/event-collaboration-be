package com.eventhub.dti.usecase.auth.impl;


import com.eventhub.dti.infrastructure.repository.RedisTokenRepository;
import com.eventhub.dti.usecase.auth.TokenBlacklistUseCase;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class TokenBlacklistUseCaseImpl implements TokenBlacklistUseCase {
  private final String REDIS_BLACKLIST_KEY = "montrack_blacklist_token:";
  private final RedisTokenRepository redisTokenRepository;

  public TokenBlacklistUseCaseImpl(RedisTokenRepository redisTokenRepository) {
    this.redisTokenRepository = redisTokenRepository;
  }

  @Override
  public void blacklistToken(String token, String expiredAt) {
    Duration duration = Duration.between(java.time.Instant.now(), java.time.Instant.parse(expiredAt));
    redisTokenRepository.saveToken(REDIS_BLACKLIST_KEY + token, duration);
  }

  @Override
  public boolean isTokenBlacklisted(String token) {
    return redisTokenRepository.isTokenBlacklisted(REDIS_BLACKLIST_KEY + token);
  }
}
