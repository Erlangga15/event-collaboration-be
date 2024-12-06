package com.eventhub.dti.infrastructure.organizer.controller;

import com.eventhub.dti.infrastructure.organizer.dto.OrganizerDTO;
import com.eventhub.dti.infrastructure.organizer.service.OrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/organizer")
public class OrganizerController {
  private final OrganizerService organizerService;

  @Autowired
  public OrganizerController(OrganizerService organizerService) {
    this.organizerService = organizerService;
  }

  @GetMapping
  public ResponseEntity<List<OrganizerDTO>> getAllOrganizers() {
    List<OrganizerDTO> organizers = organizerService.getAllOrganizers();
    return ResponseEntity.ok(organizers);
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrganizerDTO> getOrganizerById(@PathVariable Long id) {
    Optional<OrganizerDTO> organizer = organizerService.getOrganizerById(id);
    return organizer.map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @GetMapping("/email/{email}")
  public ResponseEntity<OrganizerDTO> getOrganizerByEmail(@PathVariable String email) {
    Optional<OrganizerDTO> organizer = organizerService.getOrganizerByEmail(email);
    return organizer.map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  // Create New Organizer
  @PostMapping
  public ResponseEntity<OrganizerDTO> createOrganizer(@RequestBody OrganizerDTO organizerDTO) {
    OrganizerDTO createdOrganizer = organizerService.createOrganizer(organizerDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdOrganizer);
  }

  @PutMapping("/{id}")
  public ResponseEntity<OrganizerDTO> updateOrganizer(@PathVariable Long id, @RequestBody OrganizerDTO organizerDTO) {
    Optional<OrganizerDTO> updatedOrganizer = organizerService.updateOrganizer(id, organizerDTO);
    return updatedOrganizer.map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrganizer(@PathVariable Long id) {
    boolean isDeleted = organizerService.deleteOrganizer(id);
    return isDeleted ? ResponseEntity.noContent().build()
      : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }
}
