package com.eventhub.dti.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LogoutRequestDTO {
    @NotBlank(message = "Refresh token is required")
    private String refreshToken;
    @NotBlank(message = "Access token is required")
    private String accessToken;
}
