package com.eventhub.dti.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nimbusds.jose.util.JSONArrayUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@Table(name = "Location")
public class Location {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_id_gen")
  @SequenceGenerator(name = "location_id", sequenceName = "location_id_seq", allocationSize = 1)
  @Column(name = "location_id", nullable = false)
  public Long id;

  @Size(max = 100)
  @NotNull
  @Column(name = "name", nullable = false, length = 100)
  public String name;

  @Size(max = 200)
  @NotNull
  @Column(name = "address", nullable = false)
  public String address;

  @Size(max = 50)
  @NotNull
  @Column(name = "city", nullable = false)
  public String city;

  @NotNull
  @Column(name = "coordinates", nullable = false)
  @JsonBackReference
  public String coordinates;

  @NotNull
  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

  @NotNull
  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "updated_at", nullable = false)
  private OffsetDateTime updatedAt;

  @Column(name = "deleted_at")
  private OffsetDateTime deletedAt;

}
