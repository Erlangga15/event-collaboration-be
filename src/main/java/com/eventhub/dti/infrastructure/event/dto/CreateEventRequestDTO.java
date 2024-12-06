package com.eventhub.dti.infrastructure.event.dto;

import com.eventhub.dti.entity.Event;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventRequestDTO {
  @NotNull
  private Long id;

  @NotNull
  private Long organizerId;

  @NotNull
  private Long categoryId;

  @NotNull
  private Long locationId;

  @Size(max = 100)
  @NotNull
  private String name;

  @NotNull
  private String description;

  @NotNull
  private LocalDateTime startDate;

  @NotNull
  private LocalDateTime endDate;

  @NotNull
  private Integer totalSeat;

  @NotNull
  private String available;

  @NotNull
  private String status;

  public Event toEntity() {
    Event event = new Event();
    event.setName(name);
    event.setDescription(description);
    event.setStartDate(startDate);
    event.setEndDate(endDate);
    event.setTotalSeat(totalSeat);
    event.setAvailable(available);
    event.setStatus(status);
    return event;
  }
}
