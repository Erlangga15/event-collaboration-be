package com.eventhub.dti.infrastructure.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.eventhub.dti.entity.Transaction;
import com.eventhub.dti.entity.enums.TransactionStatus;

import lombok.Data;

@Data
public class TransactionResponseDTO {
    private UUID id;
    private UserResponseDTO user;
    private EventResponseDTO event;
    private TicketResponseDTO ticket;
    private Integer quantity;
    private BigDecimal subtotalPrice;
    private BigDecimal totalPrice;
    private TransactionStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static TransactionResponseDTO fromTransaction(Transaction transaction) {
        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setId(transaction.getId());
        dto.setUser(UserResponseDTO.fromEntity(transaction.getUser()));
        dto.setEvent(EventResponseDTO.fromEvent(transaction.getEvent()));
        dto.setTicket(TicketResponseDTO.fromTicket(transaction.getTicket()));
        dto.setQuantity(transaction.getQuantity());
        dto.setSubtotalPrice(transaction.getSubtotalPrice());
        dto.setTotalPrice(transaction.getTotalPrice());
        dto.setStatus(transaction.getStatus());
        dto.setCreatedAt(transaction.getCreatedAt());
        dto.setUpdatedAt(transaction.getUpdatedAt());
        return dto;
    }
}