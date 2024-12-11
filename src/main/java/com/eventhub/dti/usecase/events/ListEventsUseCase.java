package com.eventhub.dti.usecase.events;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eventhub.dti.infrastructure.dto.EventFilterRequestDTO;
import com.eventhub.dti.infrastructure.dto.EventResponseDTO;

public interface ListEventsUseCase {
    Page<EventResponseDTO> listEvents(EventFilterRequestDTO filter, Pageable pageable);
}
