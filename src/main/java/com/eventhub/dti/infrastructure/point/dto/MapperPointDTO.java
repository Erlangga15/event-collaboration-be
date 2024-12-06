package com.eventhub.dti.infrastructure.point.dto;

import com.eventhub.dti.entity.Point;
import org.springframework.stereotype.Component;

@Component
public class MapperPointDTO {
  public CreatePointRequestDTO toDTO(Point point) {
    CreatePointRequestDTO createPointRequestDTO = new CreatePointRequestDTO();
    createPointRequestDTO.setId(point.getId());
    createPointRequestDTO.setAmount(point.getAmount());
    createPointRequestDTO.setReferralCode(point.getReferralCode());
    if (point.getDiscount() != null) {
      createPointRequestDTO.setDiscountId(point.getDiscount().getId());
    }
    createPointRequestDTO.setCreatedAt(point.getCreatedAt());
    createPointRequestDTO.setUpdatedAt(point.getUpdatedAt());
    createPointRequestDTO.setDeletedAt(point.getDeletedAt());
    return createPointRequestDTO;
  }

  // Convert from PointDTO to Point Entity
  public Point toEntity(CreatePointRequestDTO createPointRequestDTO) {
    Point point = new Point();
    point.setId(createPointRequestDTO.getId());
    point.setAmount(createPointRequestDTO.getAmount());
    point.setReferralCode(createPointRequestDTO.getReferralCode());
    point.setCreatedAt(createPointRequestDTO.getCreatedAt());
    point.setUpdatedAt(createPointRequestDTO.getUpdatedAt());
    point.setDeletedAt(createPointRequestDTO.getDeletedAt());
    return point;
  }
}
