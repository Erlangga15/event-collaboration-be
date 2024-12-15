package com.eventhub.dti.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@Table(name = "discount")
public class Discount {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "discount_id_gen")
  @SequenceGenerator(name = "discount_id_gen", sequenceName = "discount_id_seq", allocationSize = 1)
  @Column(name = "discount_id", nullable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "event_id")
  @JsonBackReference
  private Event event;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id")
  @JsonBackReference
  private User user;

  @Column(name = "voucher_code", unique = true)
  private String discountCode;

  @Column(name = "voucher_value", precision = 15, scale = 2)
  private BigDecimal discountValue;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(name = "voucher_type")
  private String discountType;

  @Column(name = "is_used")
  private boolean isUsed;

  @Column(name = "expires_at")
  private LocalDateTime expiresAt;

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
