package com.eventhub.dti.infrastructure.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.eventhub.dti.entity.Ticket;
import com.eventhub.dti.entity.enums.TicketType;

import lombok.Data;

@Data
public class TicketResponseDTO {
    private UUID id;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private TicketType type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static TicketResponseDTO fromTicket(Ticket ticket) {
        TicketResponseDTO response = new TicketResponseDTO();
        response.setId(ticket.getId());
        response.setName(ticket.getName());
        response.setPrice(ticket.getPrice());
        response.setQuantity(ticket.getQuantity());
        response.setType(ticket.getType());
        response.setCreatedAt(ticket.getCreatedAt());
        response.setUpdatedAt(ticket.getUpdatedAt());
        return response;
    }
}
