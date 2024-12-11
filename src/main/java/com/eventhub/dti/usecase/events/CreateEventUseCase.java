package com.eventhub.dti.usecase.events;

import com.eventhub.dti.infrastructure.dto.EventCreateRequestDTO;
import com.eventhub.dti.infrastructure.dto.EventResponseDTO;

public interface CreateEventUseCase {
    EventResponseDTO createEvent(EventCreateRequestDTO request);
}
