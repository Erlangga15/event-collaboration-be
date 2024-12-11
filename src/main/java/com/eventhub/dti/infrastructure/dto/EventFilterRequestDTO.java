package com.eventhub.dti.infrastructure.dto;

import java.time.LocalDateTime;

import com.eventhub.dti.entity.enums.EventCategory;
import com.eventhub.dti.entity.enums.TicketType;

import lombok.Data;

@Data
public class EventFilterRequestDTO {
    private String searchTerm;
    private EventCategory category;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String venueName;
    private TicketType ticketType;
    private String location;
}
