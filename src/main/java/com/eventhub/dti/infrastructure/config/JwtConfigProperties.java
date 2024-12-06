package com.eventhub.dti.infrastructure.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtConfigProperties {
  @Value("${jwt.secret}")
  private String secret;
  public String getSecret() {
    return secret;
  }
}
