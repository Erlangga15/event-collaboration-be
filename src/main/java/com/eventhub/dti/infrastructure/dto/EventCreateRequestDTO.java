package com.eventhub.dti.infrastructure.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.eventhub.dti.entity.enums.EventCategory;
import com.eventhub.dti.entity.enums.EventStatus;
import com.eventhub.dti.infrastructure.validation.ValidEventDates;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@ValidEventDates
public class EventCreateRequestDTO {
    @NotBlank(message = "Event name is required")
    private String name;

    private String description;

    @NotNull(message = "Start date is required")
    @Future(message = "Start date must be in the future")
    private LocalDateTime startDate;

    @NotNull(message = "End date is required")
    @Future(message = "End date must be in the future")
    private LocalDateTime endDate;

    @NotBlank(message = "Venue name is required")
    private String venueName;

    @NotBlank(message = "Venue address is required")
    private String venueAddress;

    @NotNull(message = "Category is required")
    private EventCategory category;

    private EventStatus status;

    private String image;

    @Valid
    @NotEmpty(message = "At least one ticket is required")
    private List<TicketCreateRequestDTO> tickets;

    @Valid
    private PromotionCreateRequestDTO promotion;
}
