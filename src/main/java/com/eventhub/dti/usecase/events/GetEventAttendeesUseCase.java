package com.eventhub.dti.usecase.events;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eventhub.dti.infrastructure.dto.TransactionResponseDTO;

public interface GetEventAttendeesUseCase {
    Page<TransactionResponseDTO> getEventAttendees(UUID eventId, Pageable pageable);

    List<TransactionResponseDTO> getEventAttendees(UUID eventId);
}