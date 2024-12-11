package com.eventhub.dti.usecase.events.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventhub.dti.common.exceptions.DataNotFoundException;
import com.eventhub.dti.entity.Event;
import com.eventhub.dti.infrastructure.dto.EventResponseDTO;
import com.eventhub.dti.repository.EventRepository;
import com.eventhub.dti.usecase.events.GetEventUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@Service
@RequiredArgsConstructor
public class GetEventUseCaseImpl implements GetEventUseCase {

    private final EventRepository eventRepository;

    @Override
    @Transactional(readOnly = true)
    public EventResponseDTO getEventById(UUID eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new DataNotFoundException("Event not found"));

        @SuppressWarnings("unused")
        boolean initialized = event.getTickets().isEmpty();

        return EventResponseDTO.fromEvent(event);
    }
}
