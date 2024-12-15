package com.eventhub.dti.infrastructure.discount.dto;

import com.eventhub.dti.entity.Discount;
import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CreateDiscountResponseDTO {
  private Long voucherId;
  private String eventName;
  private String discountDescription;
  private BigDecimal discountValue;
  private String discountCode;
  private String discountType;
  private String status;
  private Integer quantityAvailable;
  private LocalDate dateStart;
  private LocalDate dateEnd;

  public CreateDiscountResponseDTO(Long id, String title, String description, BigDecimal discountValue, String discountCode, String discountType, String availability, int remainingQuantity, LocalDate startDate, LocalDate endDate) {
    this.voucherId = id;
    this.eventName = title;
    this.discountDescription = description;
    this.discountValue = discountValue;
    this.discountCode = discountCode;
    this.discountType = discountType;
    this.status = availability;
    this.quantityAvailable = remainingQuantity;
    this.dateStart = LocalDate.from(startDate.atStartOfDay());
    this.dateEnd = LocalDate.from(endDate.atTime(LocalTime.MAX));
  }
}
