package com.eventhub.dti.infrastructure.organizer.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class OrganizerDTO {
  private Long id;
  private String name;
  private String email;
  private String description;
  private String phoneNumber;
  private String socialMedia;
  private String profilePictureUrl;

  // Save ID from User
  private Long userId;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
  private OffsetDateTime deletedAt;
}
