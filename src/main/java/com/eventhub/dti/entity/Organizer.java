package com.eventhub.dti.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jdk.jfr.Category;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@Table(name = "organizer")
public class Organizer {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organizer_id_gen")
  @SequenceGenerator(name = "organizer_id_gen", sequenceName = "organizer_id_seq", allocationSize = 1)
  @Column(name = "organizer_id", nullable = false)
  public Long id;

  @Size(max = 100)
  @NotNull
  @Column(name = "name", nullable = false)
  private String name;

  @Size(max = 100)
  @NotNull
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Size(max = 100)
  @NotNull
  @Column(name = "description", nullable = false)
  private String description;

  @Size(max = 50)
  @NotNull
  @Column(name = "phone_number", nullable = false)
  private String phoneNumber;

  @NotNull
  @Size(max = 50)
  @Column(name = "social_media", nullable = false)
  private String socialMedia;

  @Size(max = 100)
  @Column(name = "profile_picture_url", length = 100)
  private String profilePictureUrl;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

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
