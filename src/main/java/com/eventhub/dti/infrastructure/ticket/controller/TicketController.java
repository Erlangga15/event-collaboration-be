package com.eventhub.dti.infrastructure.ticket.controller;

import com.eventhub.dti.common.response.ApiResponse;
import com.eventhub.dti.common.response.PaginatedResponse;
import com.eventhub.dti.common.util.PaginationUtil;
import com.eventhub.dti.infrastructure.security.Claims;
import com.eventhub.dti.infrastructure.ticket.dto.CreateTicketRequestDTO;
import com.eventhub.dti.infrastructure.ticket.service.TicketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {
  private final TicketService tiketService;

  public TicketController(TicketService ticketService){
    this.tiketService = ticketService;
  }

  @PreAuthorize("hasRole('USER')")
  @GetMapping
  public ResponseEntity<?> getAllTickets(@PageableDefault(size = 10) Pageable pageable) {
    Long userId = Claims.getUserIdFromJwt();
    Page<CreateTicketRequestDTO> tickets = tiketService.getAllTicketsByUserId(userId, pageable);
    PaginatedResponse<CreateTicketRequestDTO> paginatedResponse = PaginationUtil.toPaginatedResponse(tickets);
    return ApiResponse.successfulResponse("All tickets retrieved successfully", paginatedResponse);
  }

  @PreAuthorize("hasRole('USER)")
  @GetMapping("/upcoming")
  public ResponseEntity<?> getUpcomingTickets(@PageableDefault(size = 10) Pageable pageable) {
    Long userId = Claims.getUserIdFromJwt();
    Page<CreateTicketRequestDTO> tickets = tiketService.getAllTicketsByUserId(userId, pageable);
    PaginatedResponse<CreateTicketRequestDTO> paginatedResponse = PaginationUtil.toPaginatedResponse(tickets);
    return ApiResponse.successfulResponse("Upcoming tickets retrieved successfully", paginatedResponse);
  }

  @PreAuthorize("hasRole('USER)")
  @GetMapping("/past")
  public ResponseEntity<?> getPastTickets(@PageableDefault(size = 10) Pageable pageable) {
    Long userId = Claims.getUserIdFromJwt();
    Page<CreateTicketRequestDTO> tickets = tiketService.getAllTicketsByUserId(userId, pageable);
    PaginatedResponse<CreateTicketRequestDTO> paginatedResponse = PaginationUtil.toPaginatedResponse(tickets);
    return ApiResponse.successfulResponse("Past tickets retrieved successfully", paginatedResponse);
  }
}
