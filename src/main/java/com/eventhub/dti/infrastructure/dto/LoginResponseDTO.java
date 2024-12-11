package com.eventhub.dti.infrastructure.dto;

import java.util.UUID;

import com.eventhub.dti.entity.User;
import com.eventhub.dti.entity.enums.UserRole;
import com.eventhub.dti.entity.enums.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    
    private UUID userId;
    private String email;
    private String fullName;
    private String phone;
    private UserRole role;
    private UserStatus status;

    public static LoginResponseDTO fromUserAndTokens(User user, String accessToken, String refreshToken) {
        LoginResponseDTO response = new LoginResponseDTO();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setTokenType("Bearer");
        
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());
        response.setStatus(user.getStatus());
        
        return response;
    }
}
