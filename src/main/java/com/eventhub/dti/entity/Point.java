package com.eventhub.dti.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@Table(name = "point")
public class Point {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "point_id_gen")
  @SequenceGenerator(name = "point_id_gen", sequenceName = "point_id_seq", allocationSize = 1)
  @Column(name = "point_id", nullable = false)
  private Long id;

  @NotNull
  @Column(name = "amount", nullable = false)
  private BigDecimal amount;

  @Size(max = 50)
  @Column(name = "referral_code", unique = true)
  private String referralCode;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "discount_id")
  private Discount discount;

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
