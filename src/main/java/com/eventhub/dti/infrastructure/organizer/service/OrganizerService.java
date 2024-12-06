package com.eventhub.dti.infrastructure.organizer.service;

import com.eventhub.dti.entity.Organizer;
import com.eventhub.dti.infrastructure.organizer.dto.MapperOrganizerDTO;
import com.eventhub.dti.infrastructure.organizer.dto.OrganizerDTO;
import com.eventhub.dti.infrastructure.organizer.repository.OrganizerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizerService {
  private final OrganizerRepository organizerRepository;
  private final MapperOrganizerDTO mapperOrganizerDTO;

  @Autowired
  public OrganizerService(OrganizerRepository organizerRepository, MapperOrganizerDTO mapperOrganizerDTO) {
    this.organizerRepository = organizerRepository;
    this.mapperOrganizerDTO = mapperOrganizerDTO;
  }

  public List<OrganizerDTO> getAllOrganizers() {
    List<Organizer> organizers = organizerRepository.findAll();
    return organizers.stream()
      .map(mapperOrganizerDTO::toDTO)
      .toList();
  }

  public Optional<OrganizerDTO> getOrganizerById(Long id) {
    Optional<Organizer> organizer = organizerRepository.findById(id);
    return organizer.map(mapperOrganizerDTO::toDTO);
  }

  public Optional<OrganizerDTO> getOrganizerByEmail(String email) {
    Optional<Organizer> organizer = organizerRepository.findByEmail(email);
    return organizer.map(mapperOrganizerDTO::toDTO);
  }

  // Create Organizer
  public OrganizerDTO createOrganizer(OrganizerDTO organizerDTO) {
    Organizer organizer = mapperOrganizerDTO.toEntity(organizerDTO);
    organizer = organizerRepository.save(organizer);
    return mapperOrganizerDTO.toDTO(organizer);
  }

  public Optional<OrganizerDTO> updateOrganizer(Long id, OrganizerDTO organizerDTO) {
    if (organizerRepository.existsById(id)) {
      organizerDTO.setId(id);
      Organizer organizer = mapperOrganizerDTO.toEntity(organizerDTO);
      organizer = organizerRepository.save(organizer);
      return Optional.of(mapperOrganizerDTO.toDTO(organizer));
    }
    return Optional.empty();
  }

  public boolean deleteOrganizer(Long id) {
    if (organizerRepository.existsById(id)) {
      organizerRepository.deleteById(id);
      return true;
    }
    return false;
  }

}
