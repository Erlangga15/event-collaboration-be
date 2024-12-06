package com.eventhub.dti.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Date;

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

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "date_used", nullable = false)
  private Date dateUsed;

  @NotNull
  @Column(name = "amount", nullable = false)
  private BigDecimal amount;

  @Size(max = 50)
  @NotNull
  @Column(name = "status", length = 50)
  private String status;

  @MapsId
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "transaction_id")
  @JsonBackReference
  private Transaction transaction;

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
