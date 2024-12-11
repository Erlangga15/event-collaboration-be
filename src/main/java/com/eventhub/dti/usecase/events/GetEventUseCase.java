package com.eventhub.dti.usecase.events;

import java.util.UUID;

import com.eventhub.dti.infrastructure.dto.EventResponseDTO;

public interface GetEventUseCase {
    EventResponseDTO getEventById(UUID eventId);
}
