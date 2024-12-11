package com.eventhub.dti.usecase.events;

import java.util.UUID;

import com.eventhub.dti.infrastructure.dto.EventUpdateRequestDTO;
import com.eventhub.dti.infrastructure.dto.EventResponseDTO;

public interface UpdateEventUseCase {
    EventResponseDTO updateEvent(UUID eventId, EventUpdateRequestDTO request);
}
