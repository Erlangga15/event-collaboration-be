package com.eventhub.dti.infrastructure.discount.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountSummaryDTO {
  private Long discountId;
  private String eventName;
  private String discountCode;
  private String status;
}
