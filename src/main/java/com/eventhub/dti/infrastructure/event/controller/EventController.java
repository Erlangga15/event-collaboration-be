package com.eventhub.dti.infrastructure.event.controller;

import com.eventhub.dti.common.response.ApiResponse;
import com.eventhub.dti.infrastructure.event.dto.CreateEventRequestDTO;
import com.eventhub.dti.infrastructure.security.Claims;
import com.eventhub.dti.usecase.event.CreateEventUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/events")
public class EventController {
  private final CreateEventUseCase createEventUsecase;

  public EventController(CreateEventUseCase createEventUsecase) {
    this.createEventUsecase = createEventUsecase;
  }

  @PreAuthorize("hasAuthority('SCOPE_ORGANIZER')")
  @PostMapping
  public ResponseEntity<?> createEvent(@Validated @RequestBody CreateEventRequestDTO req) {
    req.setOrganizerId(Claims.getUserIdFromJwt());
    return ApiResponse.successfulResponse("Create event success", createEventUsecase.create(req));
  }
}
