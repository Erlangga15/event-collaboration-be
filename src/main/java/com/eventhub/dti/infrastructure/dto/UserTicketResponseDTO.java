package com.eventhub.dti.infrastructure.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.eventhub.dti.entity.Transaction;
import com.eventhub.dti.entity.enums.EventCategory;
import com.eventhub.dti.entity.enums.EventStatus;

import lombok.Data;

@Data
public class UserTicketResponseDTO {
    private UUID ticketId;
    private String ticketName;
    private Integer quantity;

    private UUID eventId;
    private String eventName;
    private String eventDescription;
    private LocalDateTime eventStartDate;
    private LocalDateTime eventEndDate;
    private String venueName;
    private String venueAddress;
    private EventCategory eventCategory;
    private EventStatus eventStatus;
    private String eventImage;

    public static UserTicketResponseDTO fromTransaction(Transaction transaction) {
        UserTicketResponseDTO dto = new UserTicketResponseDTO();

        dto.setTicketId(transaction.getTicket().getId());
        dto.setTicketName(transaction.getTicket().getName());
        dto.setQuantity(transaction.getQuantity());

        dto.setEventId(transaction.getEvent().getId());
        dto.setEventName(transaction.getEvent().getName());
        dto.setEventDescription(transaction.getEvent().getDescription());
        dto.setEventStartDate(transaction.getEvent().getStartDate());
        dto.setEventEndDate(transaction.getEvent().getEndDate());
        dto.setVenueName(transaction.getEvent().getVenueName());
        dto.setVenueAddress(transaction.getEvent().getVenueAddress());
        dto.setEventCategory(transaction.getEvent().getCategory());
        dto.setEventStatus(transaction.getEvent().getStatus());
        dto.setEventImage(transaction.getEvent().getImage());

        return dto;
    }
}