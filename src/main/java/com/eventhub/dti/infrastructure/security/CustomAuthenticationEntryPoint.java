package com.eventhub.dti.infrastructure.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.eventhub.dti.common.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String message = authException.getMessage();
        if (authException instanceof InvalidBearerTokenException) {
            message = message.replace("An error occurred while attempting to decode the Jwt: ", "");
            if (message.contains("expired")) {
                message = "Token has expired. Please refresh your token or login again.";
            } else if (message.contains("invalid")) {
                message = "Invalid token format. Please login again.";
            }
        }

        Response<?> errorResponse = new Response<>(HttpStatus.UNAUTHORIZED.value(), message);
        errorResponse.setSuccess(false);

        String jsonResponse = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(jsonResponse);
    }
}
