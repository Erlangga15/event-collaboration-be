package com.eventhub.dti.infrastructure.dto;

import java.time.LocalDateTime;

import com.eventhub.dti.entity.enums.EventCategory;
import com.eventhub.dti.entity.enums.EventStatus;

import jakarta.validation.constraints.Future;
import lombok.Data;

@Data
public class EventUpdateRequestDTO {
    private String name;
    private String description;
    
    @Future(message = "Start date must be in the future")
    private LocalDateTime startDate;
    
    @Future(message = "End date must be in the future")
    private LocalDateTime endDate;
    
    private String venueName;
    private String venueAddress;
    private EventCategory category;
    private EventStatus status;
}
