package com.eventhub.dti.infrastructure.discount.dto;

import com.eventhub.dti.entity.Discount;
import org.springframework.stereotype.Component;

@Component
public class CreateDiscountResponseDTO {
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
