package com.eventhub.dti.infrastructure.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.eventhub.dti.entity.User;
import com.eventhub.dti.entity.enums.UserRole;
import com.eventhub.dti.entity.enums.UserStatus;

import lombok.Data;

@Data
public class UserResponseDTO {
    private UUID id;
    private String email;
    private String fullName;
    private String phone;
    private UserRole role;
    private UserStatus status;
    private String referralCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserResponseDTO fromEntity(User user) {
        UserResponseDTO response = new UserResponseDTO();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());
        response.setStatus(user.getStatus());
        response.setReferralCode(user.getReferralCode());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}
