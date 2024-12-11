package com.eventhub.dti.infrastructure.dto;

import java.util.UUID;

import com.eventhub.dti.entity.User;
import com.eventhub.dti.entity.enums.UserRole;
import com.eventhub.dti.entity.enums.UserStatus;

import lombok.Data;

@Data
public class UserDetailsResponseDTO {
    private UUID id;
    private String email;
    private String fullName;
    private String phone;
    private UserRole role;
    private UserStatus status;
    private String referralCode;
    private int totalPoints;
    private int totalCoupons;
    private int totalTransactions;
    private int totalReviews;

    public static UserDetailsResponseDTO fromEntity(User user) {
        UserDetailsResponseDTO response = new UserDetailsResponseDTO();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());
        response.setStatus(user.getStatus());
        response.setReferralCode(user.getReferralCode());
        response.setTotalPoints(user.getPoints().size());
        response.setTotalCoupons(user.getCoupons().size());
        response.setTotalTransactions(user.getTransactions().size());
        response.setTotalReviews(user.getReviews().size());
        return response;
    }
}
