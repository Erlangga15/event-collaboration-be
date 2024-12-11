package com.eventhub.dti.usecase.events.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventhub.dti.common.exceptions.DataNotFoundException;
import com.eventhub.dti.entity.Event;
import com.eventhub.dti.entity.Transaction;
import com.eventhub.dti.entity.enums.TransactionStatus;
import com.eventhub.dti.infrastructure.dto.TransactionResponseDTO;
import com.eventhub.dti.repository.EventRepository;
import com.eventhub.dti.repository.TransactionRepository;
import com.eventhub.dti.usecase.events.GetEventAttendeesUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetEventAttendeesUseCaseImpl implements GetEventAttendeesUseCase {

    private final EventRepository eventRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionResponseDTO> getEventAttendees(UUID eventId, Pageable pageable) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new DataNotFoundException("Event not found"));

        Page<Transaction> transactions = transactionRepository.findByEventAndStatus(event, TransactionStatus.PAID,
                pageable);
        return transactions.map(TransactionResponseDTO::fromTransaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponseDTO> getEventAttendees(UUID eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new DataNotFoundException("Event not found"));

        List<Transaction> transactions = transactionRepository.findByEventAndStatus(event, TransactionStatus.PAID);
        return transactions.stream()
                .map(TransactionResponseDTO::fromTransaction)
                .collect(Collectors.toList());
    }
}