package com.eventhub.dti.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "transaction")
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_id_gen")
  @SequenceGenerator(name = "transaction_id_gen", sequenceName = "transaction_id_seq")
  @Column(name = "transaction_id", nullable = false)
  private Long id;

  @NotNull
  @Column(name = "total_price", nullable = false)
  private BigDecimal totalPrice;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "date", nullable = false)
  private Date date;

  @NotNull
  @Column(name = "total_ticket", nullable = false)
  private Integer totalTicket;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @JsonBackReference
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "organizer_id")
  @JsonBackReference
  private Organizer organizer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ticket_id")
  @JsonBackReference
  private Ticket ticket;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "discount_id")
  @JsonBackReference
  private Discount discount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "point_id")
  @JsonBackReference
  private Point point;

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
