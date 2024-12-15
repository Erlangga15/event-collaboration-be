package com.eventhub.dti.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "\"user\"")
@AllArgsConstructor
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_id_gen")
  @SequenceGenerator(name = "review_id_gen", sequenceName = "review_id_seq", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private Long id;

  @Size(max = 100)
  @NotNull
  @Column(name = "name", nullable = false)
  private String name;

  @Size(max = 100)
  @NotNull
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Size(max = 50, message = "Password must be at least 6 characters")
  @NotNull(message = "Password is mandatory")
  @Column(name = "password", nullable = false)
  private String password;

  @Size(max = 100)
  @Column(name = "profile_picture_url", length = 100)
  private String profilePictureUrl;

  @Size(max = 50)
  @NotNull
  @Column(name = "phone_number", nullable = false)
  private String phoneNumber;

  @Size(max = 50)
  @Column(name = "referral_code", unique = true)
  private String referralCode;

  @ColumnDefault("0.0")
  @Column(name = "points", precision = 10, scale = 2)
  private BigDecimal points;

  @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  private String website;

  @NotNull
  @ColumnDefault("false")
  @Column(name = "is_onboarding_finished", nullable = false)
  private Boolean isOnboardingFinished = false;

  @NotNull
  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "update_at", nullable = false)
  private OffsetDateTime updatedAt;

  @Column(name = "deleted_at")
  private OffsetDateTime deletedAt;

  @PreUpdate
  protected void onCreate(){
    updatedAt = OffsetDateTime.now();
  }

  @PreRemove
  protected void onRemove() {
    deletedAt = OffsetDateTime.now();
  }


  public void addPoints(double points, LocalDateTime expirationDate) {
    Point point = new Point();

  }

}

