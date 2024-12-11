package com.eventhub.dti.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.eventhub.dti.infrastructure.security.CustomAuthenticationEntryPoint;
import com.eventhub.dti.usecase.auth.GetUserAuthDetailsUseCase;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final GetUserAuthDetailsUseCase getUserAuthDetailsUseCase;
    private final JwtDecoder jwtDecoder;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public AuthenticationManager authManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(getUserAuthDetailsUseCase);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }

    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        return expressionHandler;
    }

    @Bean
    public Filter loggingFilter() {
        return (ServletRequest request, ServletResponse response, FilterChain chain) -> {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            log.debug("Processing request: {} {}", httpRequest.getMethod(), httpRequest.getRequestURI());
            chain.doFilter(request, response);
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(new CorsConfigurationSourceImpl()))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/error/**").permitAll()
                            .requestMatchers("/api/v1/auth/login").permitAll()
                            .requestMatchers("/api/v1/auth/refresh").permitAll()
                            .requestMatchers("/api/v1/users/register").permitAll()
                            .requestMatchers("/api/v1/events/search").permitAll()
                            .requestMatchers("/api/v1/events/**").permitAll()
                            .requestMatchers("/demo/**").permitAll()
                            .anyRequest().authenticated();
                })
                .addFilterBefore(loggingFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> {
                    ex.authenticationEntryPoint((request, response, authException) -> {
                        log.error("Authentication Error: {} for {} {}",
                                authException.getMessage(),
                                request.getMethod(),
                                request.getRequestURI());
                        authenticationEntryPoint.commence(request, response, authException);
                    });
                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .oauth2ResourceServer(oauth2 -> {
                    oauth2.jwt(jwt -> jwt.decoder(this.jwtDecoder))
                            .authenticationEntryPoint((request, response, authException) -> {
                                log.error("OAuth2 Authentication Error: {} for {} {}",
                                        authException.getMessage(),
                                        request.getMethod(),
                                        request.getRequestURI());
                                authenticationEntryPoint.commence(request, response, authException);
                            });
                    oauth2.bearerTokenResolver(request -> {
                        String token = null;
                        String header = request.getHeader("Authorization");

                        if (header != null && header.startsWith("Bearer ")) {
                            token = header.substring(7);
                        }

                        if (token == null) {
                            Cookie[] cookies = request.getCookies();
                            if (cookies != null) {
                                for (Cookie cookie : cookies) {
                                    if ("access_token".equals(cookie.getName())) {
                                        token = cookie.getValue();
                                        break;
                                    }
                                }
                            }
                        }

                        if (token != null) {
                            String[] parts = token.split("\\.");
                            if (parts.length != 3) {
                                return null;
                            }
                            for (int i = 0; i < parts.length; i++) {
                                if (!parts[i].matches("^[A-Za-z0-9_-]+$")) {
                                    return null;
                                }
                            }
                            return token;
                        }
                        return null;
                    });
                })
                .userDetailsService(getUserAuthDetailsUseCase)
                .build();
    }
}