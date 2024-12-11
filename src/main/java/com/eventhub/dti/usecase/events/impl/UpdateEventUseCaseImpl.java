package com.eventhub.dti.usecase.events.impl;

import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventhub.dti.common.exceptions.DataNotFoundException;
import com.eventhub.dti.common.exceptions.UnauthorizedAccessException;
import com.eventhub.dti.entity.Event;
import com.eventhub.dti.infrastructure.dto.EventResponseDTO;
import com.eventhub.dti.infrastructure.dto.EventUpdateRequestDTO;
import com.eventhub.dti.repository.EventRepository;
import com.eventhub.dti.usecase.events.UpdateEventUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@Service
@RequiredArgsConstructor
public class UpdateEventUseCaseImpl implements UpdateEventUseCase {

    private final EventRepository eventRepository;

    @Override
    @Transactional
    public EventResponseDTO updateEvent(UUID eventId, EventUpdateRequestDTO request) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new DataNotFoundException("Event not found"));

        if (!event.getOrganizer().getEmail().equals(userEmail)) {
            throw new UnauthorizedAccessException("You are not authorized to update this event");
        }

        if (request.getName() != null) {
            event.setName(request.getName());
        }
        if (request.getDescription() != null) {
            event.setDescription(request.getDescription());
        }
        if (request.getStartDate() != null) {
            event.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            event.setEndDate(request.getEndDate());
        }
        if (request.getVenueName() != null) {
            event.setVenueName(request.getVenueName());
        }
        if (request.getVenueAddress() != null) {
            event.setVenueAddress(request.getVenueAddress());
        }
        if (request.getCategory() != null) {
            event.setCategory(request.getCategory());
        }
        if (request.getStatus() != null) {
            event.setStatus(request.getStatus());
        }

        Event updatedEvent = eventRepository.save(event);
        return EventResponseDTO.fromEvent(updatedEvent);
    }
}
