package com.eventhub.dti.infrastructure.transaction.dto;

import com.eventhub.dti.entity.Ticket;
import com.eventhub.dti.entity.Transaction;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
public class CreateTransactionRequestDTO {
  @NotNull
  private Long id;
  private BigDecimal totalPrice;
  private Date date;
  private Integer totalTicket;
  private Long userId;
  private Long organizerId;
  private Long ticketId;
  private Long discountId;
  private Long pointId;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
  private OffsetDateTime deletedAt;

}
