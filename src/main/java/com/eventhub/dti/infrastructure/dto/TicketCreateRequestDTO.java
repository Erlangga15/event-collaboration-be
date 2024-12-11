package com.eventhub.dti.infrastructure.dto;

import java.math.BigDecimal;

import com.eventhub.dti.entity.enums.TicketType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TicketCreateRequestDTO {
    @NotBlank(message = "Ticket name is required")
    private String name;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private BigDecimal price;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be greater than 0")
    private Integer quantity;

    @NotNull(message = "Ticket type is required")
    private TicketType type;
}