package com.eventhub.dti.infrastructure.ticket.service;

import com.eventhub.dti.entity.Ticket;
import com.eventhub.dti.entity.Transaction;
import com.eventhub.dti.infrastructure.ticket.dto.CreateTicketRequestDTO;
import com.eventhub.dti.infrastructure.ticket.repository.TicketRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TicketService {
  private final TicketRepository ticketRepository;

  public TicketService(TicketRepository ticketRepository){
    this.ticketRepository = ticketRepository;
  }


  public List<CreateTicketRequestDTO> generateTickets(Transaction transaction) {
    List<Ticket> tickets = new ArrayList<>();
    for (int i = 0; i < transaction.getTotalTicket(); i++) {
      Ticket ticket = new Ticket();
      ticket.setUser(transaction.getUser());
      ticket.setEvent(transaction.getTicket().getEvent());
      ticket.setDiscount(transaction.getDiscount());
      ticket.setTicketStatus("VALID");
      ticket.setTicketType(transaction.getTicket().getTicketType());
      ticket.setTicketQuota(transaction.getTicket().getTicketQuota());
      ticket.setTicketAvailable(transaction.getTicket().getTicketAvailable());
      ticket.setSeatNumber(transaction.getTicket().getSeatNumber());
      ticket.setDate(transaction.getDate());
      ticket.setPrice(transaction.getTotalPrice().divide(BigDecimal.valueOf(transaction.getTotalTicket())));
      ticket.setReferralCode(transaction.getTicket().getReferralCode());
      tickets.add(ticketRepository.save(ticket));
    }
    return tickets.stream().map(this::convertToDTO).collect(Collectors.toList());
  }

  public Page<CreateTicketRequestDTO> getAllTicketsByUserId(Long userId, Pageable pageable) {
    return ticketRepository.findByUserId(userId, pageable)
      .map(this::convertToDTO);
  }

//  public Page<CreateTicketRequestDTO> getUpcomingTicketsByCustomer(Long userId, Pageable pageable) {
//    LocalDateTime now = LocalDateTime.now();
//    return ticketRepository.findByUserIdAndEventDateTimeEndAfter(userId, now, pageable)
//      .map(this::convertToDTO);
//  }
//
//  public Page<CreateTicketRequestDTO> getPastTicketsByCustomer(Long userId, Pageable pageable) {
//    LocalDateTime now = LocalDateTime.now();
//    return ticketRepository.findByUserIdAndEventDateTimeEndBefore(userId, now, pageable)
//      .map(this::convertToDTO);
//  }

  private CreateTicketRequestDTO convertToDTO(Ticket ticket) {
    CreateTicketRequestDTO dto = new CreateTicketRequestDTO();
    dto.setUserId(ticket.getUser().getId());
    dto.setEventId(ticket.getEvent().getId());
    dto.setDiscountId(ticket.getDiscount().getId());
    dto.setTicketType(ticket.getTicketType());
    dto.setTicketQuota(ticket.getTicketQuota());
    dto.setTicketAvailable(ticket.getTicketAvailable());
    dto.setSeatNumber(Integer.valueOf(ticket.getSeatNumber()));
    dto.setDate(ticket.getDate());
    dto.setPrice(ticket.getPrice());
    dto.setReferralCode(ticket.getReferralCode());
    dto.setTicketStatus(String.valueOf(ticket.getTicket_status()));
    return dto;
  }

}
