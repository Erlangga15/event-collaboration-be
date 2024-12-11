package com.eventhub.dti.infrastructure.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.eventhub.dti.entity.enums.PromotionType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PromotionCreateRequestDTO {
    @NotBlank(message = "Promotion code is required")
    private String code;

    @NotNull(message = "Promotion type is required")
    private PromotionType type;

    @NotNull(message = "Promotion amount is required")
    @Min(value = 0, message = "Amount must be greater than or equal to 0")
    private BigDecimal amount;

    @NotNull(message = "Maximum uses is required")
    @Min(value = 1, message = "Maximum uses must be greater than 0")
    private Integer maxUses;

    @NotNull(message = "Promotion start date is required")
    private LocalDateTime startDate;

    @NotNull(message = "Promotion end date is required")
    private LocalDateTime endDate;
}