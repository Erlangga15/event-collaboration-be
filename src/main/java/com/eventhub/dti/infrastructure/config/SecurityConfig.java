package com.eventhub.dti.infrastructure.config;


import com.eventhub.dti.infrastructure.auth.filters.TokenBlacklist;
import com.eventhub.dti.usecase.auth.GetUserAuthDetailsUseCase;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.http.Cookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
  private final GetUserAuthDetailsUseCase getUserAuthDetailsUsecase;
  private final JwtConfigProperties jwtConfigProperties;
  private final PasswordEncoder passwordEncoder;
  private final TokenBlacklist tokenBlacklistFilter;

  public SecurityConfig(
    GetUserAuthDetailsUseCase getUserAuthDetailsUsecase,
    JwtConfigProperties jwtConfigProperties,
    PasswordEncoder passwordEncoder,
    TokenBlacklist tokenBlacklistFilter) {
    this.getUserAuthDetailsUsecase = getUserAuthDetailsUsecase;
    this.jwtConfigProperties = jwtConfigProperties;
    this.passwordEncoder = passwordEncoder;
    this.tokenBlacklistFilter = tokenBlacklistFilter;
  }

  @Bean
  public AuthenticationManager authManager() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(getUserAuthDetailsUsecase);
    authProvider.setPasswordEncoder(passwordEncoder);
    return new ProviderManager(authProvider);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
      .csrf(AbstractHttpConfigurer::disable)
      .cors(cors -> cors.configurationSource(new CorsConfigurationSourceImpl()))
      .authorizeHttpRequests(auth -> auth
        // Define public routes
        .requestMatchers("/error/**").permitAll()
        .requestMatchers("/api/v1/auth/login").permitAll()
        .requestMatchers("/api/v1/users/register").permitAll()

        // Define rest of the routes to be private
        .anyRequest().authenticated())
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .oauth2ResourceServer(oauth2 -> {
        oauth2.jwt(jwt -> jwt.decoder(jwtDecoder()));
        oauth2.bearerTokenResolver(request -> {
          Cookie[] cookies = request.getCookies();
          if (cookies != null) {
            for (Cookie cookie : cookies) {
              if (cookie.getName().equals("SID")) {
                return cookie.getValue();
              }
            }
          }
          var header = request.getHeader("Authorization");
          if (header != null) {
            return header.replace("Bearer ", "");
          }

          return null;
        });
      })
      .addFilterAfter(tokenBlacklistFilter, BearerTokenAuthenticationFilter.class)
      .userDetailsService(getUserAuthDetailsUsecase)
      .build();
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    SecretKey originalKey = new SecretKeySpec(jwtConfigProperties.getSecret().getBytes(), "HmacSHA256");
    return NimbusJwtDecoder.withSecretKey(originalKey).build();
  }

  @Bean
  public JwtEncoder jwtEncoder() {
    SecretKey key = new SecretKeySpec(jwtConfigProperties.getSecret().getBytes(), "HmacSHA256");
    JWKSource<SecurityContext> immutableSecret = new ImmutableSecret<SecurityContext>(key);
    return new NimbusJwtEncoder(immutableSecret);
  }
}
