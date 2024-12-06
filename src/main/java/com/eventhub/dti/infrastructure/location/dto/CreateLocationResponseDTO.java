package com.eventhub.dti.infrastructure.location.dto;

import com.eventhub.dti.entity.Location;
import com.eventhub.dti.infrastructure.point.dto.CreatePointRequestDTO;
import com.nimbusds.jose.util.JSONArrayUtils;
import org.springframework.stereotype.Component;

@Component
public class CreateLocationResponseDTO {
  public CreateLocationRequestDTO toDTO(Location location) {
    CreateLocationRequestDTO createLocationRequestDTO = new CreateLocationRequestDTO();
    createLocationRequestDTO.setId(location.getId());
    createLocationRequestDTO.setName(location.getName());
    createLocationRequestDTO.setAddress(location.getAddress());
    createLocationRequestDTO.setCity(location.getCity());
    createLocationRequestDTO.setCoordinates(location.getCoordinates().toString());
    createLocationRequestDTO.setCreatedAt(location.getCreatedAt());
    createLocationRequestDTO.setUpdatedAt(location.getUpdatedAt());
    createLocationRequestDTO.setDeletedAt(location.getDeletedAt());
    return createLocationRequestDTO;
  }

  // Convert from LocationDTO to Location Entity
  public Location toEntity(CreateLocationRequestDTO createLocationRequestDTO) {
    Location location = new Location();
    location.setId(createLocationRequestDTO.getId());
    location.setName(createLocationRequestDTO.getName());
    location.setAddress(createLocationRequestDTO.getAddress());
    location.setCity(createLocationRequestDTO.getCity());
    location.setCoordinates(createLocationRequestDTO.getCoordinates());
    location.setCreatedAt(createLocationRequestDTO.getCreatedAt());
    location.setUpdatedAt(createLocationRequestDTO.getUpdatedAt());
    location.setDeletedAt(createLocationRequestDTO.getDeletedAt());
    return location;
  }
}
