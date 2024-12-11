package com.eventhub.dti.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UseReferralRequestDTO {
    @NotBlank(message = "Referral code is required")
    private String referralCode;
}
