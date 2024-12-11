package com.eventhub.dti.usecase.events.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventhub.dti.common.exceptions.DataNotFoundException;
import com.eventhub.dti.entity.Event;
import com.eventhub.dti.entity.Promotion;
import com.eventhub.dti.entity.Ticket;
import com.eventhub.dti.entity.User;
import com.eventhub.dti.entity.enums.EventStatus;
import com.eventhub.dti.infrastructure.dto.EventCreateRequestDTO;
import com.eventhub.dti.infrastructure.dto.EventResponseDTO;
import com.eventhub.dti.infrastructure.service.CloudinaryService;
import com.eventhub.dti.repository.EventRepository;
import com.eventhub.dti.repository.UserRepository;
import com.eventhub.dti.usecase.events.CreateEventUseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CreateEventUseCaseImpl implements CreateEventUseCase {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    @Transactional
    public EventResponseDTO createEvent(EventCreateRequestDTO request) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User organizer = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        Event event = new Event();
        event.setName(request.getName());
        event.setDescription(request.getDescription());
        event.setStartDate(request.getStartDate());
        event.setEndDate(request.getEndDate());
        event.setVenueName(request.getVenueName());
        event.setVenueAddress(request.getVenueAddress());
        event.setCategory(request.getCategory());
        event.setOrganizer(organizer);
        event.setStatus(request.getStatus() != null ? request.getStatus() : EventStatus.DRAFT);

        if (request.getImage() != null && !request.getImage().isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(request.getImage());
            event.setImage(imageUrl);
        }

        request.getTickets().forEach(ticketRequest -> {
            Ticket ticket = new Ticket();
            ticket.setName(ticketRequest.getName());
            ticket.setPrice(ticketRequest.getPrice());
            ticket.setQuantity(ticketRequest.getQuantity());
            ticket.setType(ticketRequest.getType());
            ticket.setEvent(event);
            event.getTickets().add(ticket);
        });

        if (request.getPromotion() != null) {
            Promotion promotion = new Promotion();
            promotion.setCode(request.getPromotion().getCode());
            promotion.setType(request.getPromotion().getType());
            promotion.setAmount(request.getPromotion().getAmount());
            promotion.setStartDate(request.getPromotion().getStartDate());
            promotion.setEndDate(request.getPromotion().getEndDate());
            promotion.setMaxUses(request.getPromotion().getMaxUses());
            promotion.setEvent(event);
            event.getPromotions().add(promotion);
        }

        Event savedEvent = eventRepository.save(event);
        return EventResponseDTO.fromEvent(savedEvent);
    }
}
