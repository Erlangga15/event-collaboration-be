package com.eventhub.dti.infrastructure.location.controller;

import com.eventhub.dti.infrastructure.location.dto.CreateLocationRequestDTO;
import com.eventhub.dti.infrastructure.location.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/locations")
public class LocationController {
  private final LocationService locationService;

  @Autowired
  public LocationController(LocationService locationService) {
    this.locationService = locationService;
  }

  @GetMapping
  public ResponseEntity<List<CreateLocationRequestDTO>> getAllLocations() {
    List<CreateLocationRequestDTO> locations = locationService.getAllLocations();
    return ResponseEntity.ok(locations);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CreateLocationRequestDTO> getLocationById(@PathVariable Long id) {
    Optional<CreateLocationRequestDTO> location = locationService.getLocationById(id);
    return location.map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @PostMapping
  public ResponseEntity<CreateLocationRequestDTO> createLocation(@RequestBody CreateLocationRequestDTO createLocationRequestDTO) {
    CreateLocationRequestDTO createdLocation = locationService.createLocation(createLocationRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdLocation);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CreateLocationRequestDTO> updateLocation(@PathVariable Long id, @RequestBody CreateLocationRequestDTO createLocationRequestDTO) {
    Optional<CreateLocationRequestDTO> updatedLocation = locationService.updateLocation(id, createLocationRequestDTO);
    return updatedLocation.map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
    boolean isDeleted = locationService.deleteLocation(id);
    return isDeleted ? ResponseEntity.noContent().build()
      : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }
}
