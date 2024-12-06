package com.eventhub.dti.infrastructure.ticket.dto;


import com.eventhub.dti.entity.Ticket;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTicketRequestDTO {
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

  public Ticket toEntity() {
    Ticket ticket = new Ticket();
    ticket.setTicketType(ticketType);
    ticket.setTicketQuota(ticketQuota);
    ticket.setTicketAvailable(ticketAvailable);
    ticket.setSeatNumber(String.valueOf(seatNumber));
    ticket.setDate(date);
    ticket.setPrice(price);
    ticket.setReferralCode(referralCode);
    ticket.setTicketStatus(ticketStatus);
    return ticket;
  }
}
