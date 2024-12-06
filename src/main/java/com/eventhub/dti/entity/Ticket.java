package com.eventhub.dti.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
@Table(name = "ticket")
public class Ticket {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_id_gen")
  @SequenceGenerator(name = "ticket_id_gen", sequenceName = "ticket_id_seq", allocationSize = 1)
  @Column(name = "ticket_id", nullable = false)
  private Long id;

  @NotNull
  @Size(max = 50)
  @Column(name = "ticket_type", nullable = false)
  private String ticketType;

  @NotNull
  @Positive
  @Column(name = "ticket_quota", nullable = false)
  private Integer ticketQuota;

  @NotNull
  @Positive
  @Column(name = "ticket_available", nullable = false)
  private Integer ticketAvailable;

  @Size(max = 100)
  @Column(name = "seat_number")
  private String seatNumber;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "date", nullable = false)
  private Date date;

  @NotNull
  @Positive
  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @Size(max = 50)
  @Column(name = "referral_code", unique = true)
  private String referralCode;

  @ColumnDefault("'VALID'")
  @Column(name = "ticket_status", length = Integer.MAX_VALUE)
  private String ticket_status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "event_id")
  private Event event;

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

  public void setTicketStatus(String cancelled) {
  }

}
