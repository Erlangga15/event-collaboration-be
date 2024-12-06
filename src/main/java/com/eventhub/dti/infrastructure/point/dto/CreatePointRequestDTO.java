package com.eventhub.dti.infrastructure.point.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class CreatePointRequestDTO {
  private Long id;
  private BigDecimal amount;
  private String referralCode;

  // Use ID from Discount, not object Discount
  private Long discountId;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
  private OffsetDateTime deletedAt;
}
