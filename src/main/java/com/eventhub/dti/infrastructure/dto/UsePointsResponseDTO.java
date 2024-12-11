package com.eventhub.dti.infrastructure.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
public class UsePointsResponseDTO {
    private UUID userId;
    private Integer pointsUsed;
    private BigDecimal originalPrice;
    private BigDecimal discountAmount;
    private BigDecimal finalPrice;
}
