package com.eventhub.dti.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@Table(name = "review")
@NoArgsConstructor
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_id_gen")
  @SequenceGenerator(name = "review_id_gen", sequenceName = "review_id_gen", allocationSize = 1)
  private Long id;

  @NotNull
  @ColumnDefault("0")
  @Column(name = "rating", nullable = false, precision = 10, scale = 5)
  private BigDecimal rating;

  @Size(max = 100)
  @NotNull
  @Column(name = "description", nullable = false)
  private String description;

  @Size(max = 100)
  @Column(name = "image_url", length = 100)
  private String imageUrl;

  @Size(max = 50)
  @NotNull
  @Column(name = "status", length = 50)
  private String status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "event_id")
  private Event event;

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

  @PrePersist
  protected void onCreate() {
    createdAt = OffsetDateTime.now();
    updatedAt = OffsetDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = OffsetDateTime.now();
  }

  @PreRemove
  protected void onRemove() {
    deletedAt = OffsetDateTime.now();
  }
}
