package com.eventhub.dti.infrastructure.category.dto;

import com.eventhub.dti.infrastructure.event.dto.CreateEventRequestDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
public class CreateCategoryRequestDTO {
  private Long id;
  private String name;
  private Set<CreateEventRequestDTO> events;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
  private OffsetDateTime deletedAt;
}
