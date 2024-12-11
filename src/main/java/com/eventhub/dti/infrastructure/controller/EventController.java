package com.eventhub.dti.infrastructure.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventhub.dti.common.response.Response;
import com.eventhub.dti.infrastructure.dto.EventCreateRequestDTO;
import com.eventhub.dti.infrastructure.dto.EventFilterRequestDTO;
import com.eventhub.dti.infrastructure.dto.EventResponseDTO;
import com.eventhub.dti.infrastructure.dto.EventUpdateRequestDTO;
import com.eventhub.dti.infrastructure.dto.TransactionResponseDTO;
import com.eventhub.dti.usecase.events.CreateEventUseCase;
import com.eventhub.dti.usecase.events.GetEventAttendeesUseCase;
import com.eventhub.dti.usecase.events.GetEventUseCase;
import com.eventhub.dti.usecase.events.ListEventsUseCase;
import com.eventhub.dti.usecase.events.UpdateEventUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final CreateEventUseCase createEventUseCase;
    private final GetEventUseCase getEventUseCase;
    private final ListEventsUseCase listEventsUseCase;
    private final UpdateEventUseCase updateEventUseCase;
    private final GetEventAttendeesUseCase getEventAttendeesUseCase;

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ORGANIZER')")
    public ResponseEntity<Response<EventResponseDTO>> createEvent(@Valid @RequestBody EventCreateRequestDTO request) {
        EventResponseDTO response = createEventUseCase.createEvent(request);
        return Response.successfulResponse("Event created successfully", response);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Response<EventResponseDTO>> getEvent(@PathVariable UUID eventId) {
        EventResponseDTO response = getEventUseCase.getEventById(eventId);
        return Response.successfulResponse("Event retrieved successfully", response);
    }

    @GetMapping
    public ResponseEntity<Response<Page<EventResponseDTO>>> listEvents(
            EventFilterRequestDTO filter,
            Pageable pageable) {
        Page<EventResponseDTO> response = listEventsUseCase.listEvents(filter, pageable);
        return Response.successfulResponse("Events retrieved successfully", response);
    }

    @PutMapping("/{eventId}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ORGANIZER')")
    public ResponseEntity<Response<EventResponseDTO>> updateEvent(
            @PathVariable UUID eventId,
            @Valid @RequestBody EventUpdateRequestDTO request) {
        EventResponseDTO response = updateEventUseCase.updateEvent(eventId, request);
        return Response.successfulResponse("Event updated successfully", response);
    }

    @GetMapping("/{eventId}/attendees")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ORGANIZER')")
    public ResponseEntity<Response<Page<TransactionResponseDTO>>> getEventAttendees(
            @PathVariable UUID eventId,
            Pageable pageable) {
        return Response.successfulResponse(
                "Successfully retrieved event attendees",
                getEventAttendeesUseCase.getEventAttendees(eventId, pageable));
    }

    @GetMapping("/{eventId}/attendees/all")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ORGANIZER')")
    public ResponseEntity<Response<List<TransactionResponseDTO>>> getAllEventAttendees(
            @PathVariable UUID eventId) {
        return Response.successfulResponse(
                "Successfully retrieved all event attendees",
                getEventAttendeesUseCase.getEventAttendees(eventId));
    }
}
