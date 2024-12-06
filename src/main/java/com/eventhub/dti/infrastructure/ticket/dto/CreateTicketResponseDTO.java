package com.eventhub.dti.infrastructure.ticket.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor@AllArgsConstructor
public class CreateTicketResponseDTO {
  @NotNull
  private Long userId;

  @NotNull
  private Long eventId;

  @NotNull
  private Long discountId;

  @NotNull
  private String ticketType;

  @NotNull
  private Integer ticketQuota;

  @NotNull
  private Integer ticketAvailable;

  @NotNull
  private Integer seatNumber;

  @NotNull
  private Date date;

  @NotNull
  private BigDecimal price;

  @NotNull
  private String referralCode;

  @NotNull
  private String ticketStatus;

}
