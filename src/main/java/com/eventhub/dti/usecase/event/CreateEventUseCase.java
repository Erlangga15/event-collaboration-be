package com.eventhub.dti.usecase.event;

import com.eventhub.dti.infrastructure.event.dto.CreateEventRequestDTO;
import com.eventhub.dti.infrastructure.event.dto.CreateEventResponseDTO;

public interface CreateEventUseCase {
  CreateEventResponseDTO create(CreateEventRequestDTO req);
}
