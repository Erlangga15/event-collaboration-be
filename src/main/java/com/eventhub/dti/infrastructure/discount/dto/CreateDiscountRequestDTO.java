package com.eventhub.dti.infrastructure.discount.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;

@Getter
@Setter
public class CreateDiscountRequestDTO {

  private Long eventId;
  private String discountCode;
  private BigDecimal discountValue;
  private String description;
  private String discountType;
  private LocalDate startDate;
  private LocalDate endDate;
  private Integer quantityLimit;
}
