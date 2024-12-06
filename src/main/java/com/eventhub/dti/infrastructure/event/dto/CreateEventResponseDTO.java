package com.eventhub.dti.infrastructure.event.dto;

import com.eventhub.dti.entity.Category;
import com.eventhub.dti.entity.Location;
import com.eventhub.dti.entity.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventResponseDTO {
  private Long Id;

  @NotNull
  private User organizer;

  @NotNull
  private Category category;

  @NotNull
  private Location location;

  @NotNull
  private String name;

  @Size(max = 100)
  @NotNull
  private String description;

  @NotNull
  private LocalDate startDate;

  @NotNull
  private LocalDate endDate;

  @NotNull
  private Integer totalSeat;

  @NotNull
  private String status;


  public CreateEventResponseDTO(Long id, User organizer, Category category, javax.xml.stream.Location location,
                                @Size(max = 100)
                                @NotNull String name,
                                @Size(max = 100)
                                @NotNull String description, String available, LocalDateTime startDate, LocalDateTime endDate, Integer totalSeat,
                                @Size(max = 50)
                                @NotNull String status) {
  }
}
