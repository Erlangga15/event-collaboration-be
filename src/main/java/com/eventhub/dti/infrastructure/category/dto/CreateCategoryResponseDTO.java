package com.eventhub.dti.infrastructure.category.dto;

import com.eventhub.dti.entity.Category;
import com.eventhub.dti.entity.Event;
import com.eventhub.dti.infrastructure.event.dto.CreateEventRequestDTO;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CreateCategoryResponseDTO {
  public CreateCategoryRequestDTO toDTO(Category category) {
    CreateCategoryRequestDTO createCategoryRequestDTO = new CreateCategoryRequestDTO();
    createCategoryRequestDTO.setId(category.getId());
    createCategoryRequestDTO.setName(category.getName());
    createCategoryRequestDTO.setCreatedAt(category.getCreatedAt());
    createCategoryRequestDTO.setUpdatedAt(category.getUpdatedAt());
    createCategoryRequestDTO.setDeletedAt(category.getDeletedAt());

    Set<CreateEventRequestDTO> createEventRequestDTOSet = category.getEvents().stream()
      .map(this::toEventDTO)
      .collect(Collectors.toSet());
    createCategoryRequestDTO.setEvents(createEventRequestDTOSet);

    return createCategoryRequestDTO;
  }

  private CreateEventRequestDTO toEventDTO(Event event) {
    CreateEventRequestDTO createEventRequestDTO = new CreateEventRequestDTO();
    createEventRequestDTO.setId(event.getId());
    createEventRequestDTO.setName(event.getName());
    createEventRequestDTO.setDescription(event.getDescription());
    return createEventRequestDTO;
  }

  public Category toEntity(CreateCategoryRequestDTO createCategoryRequestDTO) {
    Category category = new Category();
    category.setId(createCategoryRequestDTO.getId());
    category.setName(createCategoryRequestDTO.getName());
    category.setCreatedAt(createCategoryRequestDTO.getCreatedAt());
    category.setUpdatedAt(createCategoryRequestDTO.getUpdatedAt());
    category.setDeletedAt(createCategoryRequestDTO.getDeletedAt());

    return category;
  }
}
