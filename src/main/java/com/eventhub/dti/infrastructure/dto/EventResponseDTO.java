package com.eventhub.dti.infrastructure.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.eventhub.dti.entity.Event;
import com.eventhub.dti.entity.enums.EventCategory;
import com.eventhub.dti.entity.enums.EventStatus;

import lombok.Data;

@Data
public class EventResponseDTO {
    private UUID id;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String venueName;
    private String venueAddress;
    private EventCategory category;
    private EventStatus status;
    private UserResponseDTO organizer;
    private List<TicketResponseDTO> tickets;
    private List<PromotionResponseDTO> promotions;
    private String image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static EventResponseDTO fromEvent(Event event) {
        EventResponseDTO response = new EventResponseDTO();
        response.setId(event.getId());
        response.setName(event.getName());
        response.setDescription(event.getDescription());
        response.setStartDate(event.getStartDate());
        response.setEndDate(event.getEndDate());
        response.setVenueName(event.getVenueName());
        response.setVenueAddress(event.getVenueAddress());
        response.setCategory(event.getCategory());
        response.setStatus(event.getStatus());
        response.setImage(event.getImage());
        response.setOrganizer(UserResponseDTO.fromEntity(event.getOrganizer()));
        response.setTickets(event.getTickets().stream()
                .map(TicketResponseDTO::fromTicket)
                .collect(Collectors.toList()));
        response.setPromotions(event.getPromotions().stream()
                .map(PromotionResponseDTO::fromPromotion)
                .collect(Collectors.toList()));
        response.setCreatedAt(event.getCreatedAt());
        response.setUpdatedAt(event.getUpdatedAt());
        return response;
    }
}
