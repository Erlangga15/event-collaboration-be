package com.eventhub.dti.infrastructure.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsePointsRequestDTO {
    @NotNull(message = "Points amount is required")
    @Min(value = 1, message = "Points must be greater than 0")
    private Integer points;

    @NotNull(message = "Ticket price is required")
    @Min(value = 0, message = "Ticket price must be greater than or equal to 0")
    private BigDecimal ticketPrice;
}
