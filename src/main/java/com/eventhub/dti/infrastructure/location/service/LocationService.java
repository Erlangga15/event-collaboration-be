package com.eventhub.dti.infrastructure.location.service;

import com.eventhub.dti.entity.Location;
import com.eventhub.dti.infrastructure.location.dto.CreateLocationRequestDTO;
import com.eventhub.dti.infrastructure.location.dto.CreateLocationResponseDTO;
import com.eventhub.dti.infrastructure.location.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
  private final LocationRepository locationRepository;
  private final CreateLocationResponseDTO createLocationResponseDTO;

  @Autowired
  public LocationService(LocationRepository locationRepository, CreateLocationResponseDTO createLocationResponseDTO) {
    this.locationRepository = locationRepository;
    this.createLocationResponseDTO = createLocationResponseDTO;
  }

  public List<CreateLocationRequestDTO> getAllLocations() {
    List<Location> locations = locationRepository.findAll();
    return locations.stream()
      .map(createLocationResponseDTO::toDTO)
      .toList();
  }

  public Optional<CreateLocationRequestDTO> getLocationById(Long id) {
    Optional<Location> location = locationRepository.findById(id);
    return location.map(createLocationResponseDTO::toDTO);
  }

  public CreateLocationRequestDTO createLocation(CreateLocationRequestDTO createLocationRequestDTO) {
    Location location = createLocationResponseDTO.toEntity(createLocationRequestDTO);
    location = locationRepository.save(location);
    return createLocationResponseDTO.toDTO(location);
  }

  public Optional<CreateLocationRequestDTO> updateLocation(Long id, CreateLocationRequestDTO createLocationRequestDTO) {
    if (locationRepository.existsById(id)) {
      createLocationRequestDTO.setId(id);  // Pastikan ID sudah ter-set
      Location location = createLocationResponseDTO.toEntity(createLocationRequestDTO);
      location = locationRepository.save(location);
      return Optional.of(createLocationResponseDTO.toDTO(location));
    }
    return Optional.empty();
  }

  public boolean deleteLocation(Long id) {
    if (locationRepository.existsById(id)) {
      locationRepository.deleteById(id);
      return true;
    }
    return false;
  }
}
