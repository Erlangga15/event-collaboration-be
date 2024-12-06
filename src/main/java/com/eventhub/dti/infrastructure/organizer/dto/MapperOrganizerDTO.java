package com.eventhub.dti.infrastructure.organizer.dto;

import com.eventhub.dti.entity.Organizer;
import org.springframework.stereotype.Component;

@Component
public class MapperOrganizerDTO {
  public OrganizerDTO toDTO(Organizer organizer) {
    OrganizerDTO organizerDTO = new OrganizerDTO();
    organizerDTO.setId(organizer.getId());
    organizerDTO.setName(organizer.getName());
    organizerDTO.setEmail(organizer.getEmail());
    organizerDTO.setDescription(organizer.getDescription());
    organizerDTO.setPhoneNumber(organizer.getPhoneNumber());
    organizerDTO.setSocialMedia(organizer.getSocialMedia());
    organizerDTO.setProfilePictureUrl(organizer.getProfilePictureUrl());
    if (organizer.getUser() != null) {
      organizerDTO.setUserId(organizer.getUser().getId());
    }
    organizerDTO.setCreatedAt(organizer.getCreatedAt());
    organizerDTO.setUpdatedAt(organizer.getUpdatedAt());
    organizerDTO.setDeletedAt(organizer.getDeletedAt());
    return organizerDTO;
  }

  // Convert from OrganizerDTO to Organizer Entity
  public Organizer toEntity(OrganizerDTO organizerDTO) {
    Organizer organizer = new Organizer();
    organizer.setId(organizerDTO.getId());
    organizer.setName(organizerDTO.getName());
    organizer.setEmail(organizerDTO.getEmail());
    organizer.setDescription(organizerDTO.getDescription());
    organizer.setPhoneNumber(organizerDTO.getPhoneNumber());
    organizer.setSocialMedia(organizerDTO.getSocialMedia());
    organizer.setProfilePictureUrl(organizerDTO.getProfilePictureUrl());
    organizer.setCreatedAt(organizerDTO.getCreatedAt());
    organizer.setUpdatedAt(organizerDTO.getUpdatedAt());
    organizer.setDeletedAt(organizerDTO.getDeletedAt());
    return organizer;
  }
}
