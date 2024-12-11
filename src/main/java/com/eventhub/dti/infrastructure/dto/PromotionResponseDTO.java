package com.eventhub.dti.infrastructure.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.eventhub.dti.entity.Promotion;
import com.eventhub.dti.entity.enums.PromotionType;

import lombok.Data;

@Data
public class PromotionResponseDTO {
    private UUID id;
    private String code;
    private PromotionType type;
    private BigDecimal amount;
    private Integer maxUses;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PromotionResponseDTO fromPromotion(Promotion promotion) {
        PromotionResponseDTO response = new PromotionResponseDTO();
        response.setId(promotion.getId());
        response.setCode(promotion.getCode());
        response.setType(promotion.getType());
        response.setAmount(promotion.getAmount());
        response.setMaxUses(promotion.getMaxUses());
        response.setStartDate(promotion.getStartDate());
        response.setEndDate(promotion.getEndDate());
        response.setCreatedAt(promotion.getCreatedAt());
        response.setUpdatedAt(promotion.getUpdatedAt());
        return response;
    }
}