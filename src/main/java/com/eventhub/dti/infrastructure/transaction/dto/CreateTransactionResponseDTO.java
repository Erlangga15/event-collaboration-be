package com.eventhub.dti.infrastructure.transaction.dto;

import com.eventhub.dti.entity.Transaction;
import com.eventhub.dti.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Component
public class CreateTransactionResponseDTO {
  public CreateTransactionRequestDTO toDTO(Transaction transaction) {
    CreateTransactionRequestDTO transactionDTO = new CreateTransactionRequestDTO();
    transactionDTO.setId(transaction.getId());
    transactionDTO.setTotalPrice(transaction.getTotalPrice());
    transactionDTO.setDate(transaction.getDate());
    transactionDTO.setTotalTicket(transaction.getTotalTicket());

    transactionDTO.setUserId(transaction.getUser().getId());
    transactionDTO.setOrganizerId(transaction.getOrganizer().getId());
    transactionDTO.setTicketId(transaction.getTicket().getId());
    transactionDTO.setDiscountId(transaction.getDiscount().getId());
    transactionDTO.setPointId(transaction.getPoint().getId());

    transactionDTO.setCreatedAt(transaction.getCreatedAt());
    transactionDTO.setUpdatedAt(transaction.getUpdatedAt());
    transactionDTO.setDeletedAt(transaction.getDeletedAt());

    return transactionDTO;
  }

  public Transaction toEntity(CreateTransactionRequestDTO transactionDTO) {
    Transaction transaction = new Transaction();
    transaction.setId(transactionDTO.getId());
    transaction.setTotalPrice(transactionDTO.getTotalPrice());
    transaction.setDate(transactionDTO.getDate());
    transaction.setTotalTicket(transactionDTO.getTotalTicket());

    transaction.setUser(transaction.getUser());
    transaction.setOrganizer(transaction.getOrganizer());
    transaction.setTicket(transaction.getTicket());
    transaction.setDiscount(transaction.getDiscount());
    transaction.setPoint(transaction.getPoint());

    transaction.setCreatedAt(transactionDTO.getCreatedAt());
    transaction.setUpdatedAt(transactionDTO.getUpdatedAt());
    transaction.setDeletedAt(transactionDTO.getDeletedAt());

    return transaction;
  }
}
