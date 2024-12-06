package com.eventhub.dti.infrastructure.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CreateReviewRequestDTO {
  @NotNull
  private Long Id;

  @Min(1)
  @Max(5)
  private Integer rating;

  @NotBlank
  private String description;
}
