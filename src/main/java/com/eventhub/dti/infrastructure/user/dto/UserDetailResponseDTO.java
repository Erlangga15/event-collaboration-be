package com.eventhub.dti.infrastructure.user.dto;


import com.eventhub.dti.common.response.PaginatedResponse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.context.annotation.Role;

import java.io.Serializable;
import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailResponseDTO implements Serializable {
  private Long userId;
  private String photoProfileUrl;
  private String fullName;
  private String email;
  private String website;
  private PaginatedResponse events;

}
