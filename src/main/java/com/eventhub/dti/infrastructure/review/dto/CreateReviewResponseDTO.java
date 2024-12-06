package com.eventhub.dti.infrastructure.review.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateReviewResponseDTO {

  private Long id;
  private Long userId;
  private Long eventId;
  private Integer rating;
  private String description;
  private String imageUrl;
  private String status;

  private String eventTitle;
  private LocalDateTime eventDateTimeStart;
  private LocalDateTime eventDateTimeEnd;

  private String userName;

  public void getEventTitle(@NotBlank(message = "Title is mandatory") String title) {

  }
}
