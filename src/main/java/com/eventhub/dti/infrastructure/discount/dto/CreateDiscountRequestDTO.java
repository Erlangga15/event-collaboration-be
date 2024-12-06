package com.eventhub.dti.infrastructure.discount.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Date;

@Getter
@Setter
public class CreateDiscountRequestDTO {
  private Long id;
  private Long eventId;
  private Date dateUsed;
  private BigDecimal amount;
  private String status;
  private Long transactionId;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
  private OffsetDateTime deletedAt;
}
