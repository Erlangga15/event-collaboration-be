package com.eventhub.dti.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TokenRefreshRequestDTO {
    @NotBlank(message = "Refresh token is required")
    private String refreshToken;
}