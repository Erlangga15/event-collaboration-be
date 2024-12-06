package com.eventhub.dti.infrastructure.location.dto;

import com.nimbusds.jose.util.JSONArrayUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class CreateLocationRequestDTO {
  private Long id;
  private String name;
  private String address;
  private String city;

  // Representation coordinates as String
  private String coordinates;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
  private OffsetDateTime deletedAt;

}
