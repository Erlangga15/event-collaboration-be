package com.eventhub.dti.infrastructure.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDTO {
  @NotNull
  @Size(max = 100)
  private String email;

  @NotNull
  private String password;
}
