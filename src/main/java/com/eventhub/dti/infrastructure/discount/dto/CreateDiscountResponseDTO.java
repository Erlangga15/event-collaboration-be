package com.eventhub.dti.infrastructure.discount.dto;

import com.eventhub.dti.entity.Discount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CreateDiscountResponseDTO {
  private Long id;
  private Long eventId;
  private Date dateUsed;
  private BigDecimal amount;
  private String status;
  private Long transactionId;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
  private OffsetDateTime deletedAt;

  public CreateDiscountRequestDTO toDTO(Discount discount) {
    CreateDiscountRequestDTO createDiscountRequestDTO = new CreateDiscountRequestDTO();
    createDiscountRequestDTO.setId(discount.getId());
    createDiscountRequestDTO.setEventId(discount.getEvent().getId());
    createDiscountRequestDTO.setDateUsed(discount.getDateUsed());
    createDiscountRequestDTO.setAmount(discount.getAmount());
    createDiscountRequestDTO.setStatus(discount.getStatus());
    createDiscountRequestDTO.setTransactionId(discount.getTransaction().getId());
    createDiscountRequestDTO.setCreatedAt(discount.getCreatedAt());
    createDiscountRequestDTO.setUpdatedAt(discount.getUpdatedAt());
    createDiscountRequestDTO.setDeletedAt(discount.getDeletedAt());

    return createDiscountRequestDTO;
  }

  public Discount toEntity(CreateDiscountRequestDTO createDiscountRequestDTO) {
    Discount discount = new Discount();
    discount.setId(createDiscountRequestDTO.getId());
    discount.setDateUsed(createDiscountRequestDTO.getDateUsed());
    discount.setAmount(createDiscountRequestDTO.getAmount());
    discount.setStatus(createDiscountRequestDTO.getStatus());
    discount.setCreatedAt(createDiscountRequestDTO.getCreatedAt());
    discount.setUpdatedAt(createDiscountRequestDTO.getUpdatedAt());
    discount.setDeletedAt(createDiscountRequestDTO.getDeletedAt());

    return discount;
  }
}
