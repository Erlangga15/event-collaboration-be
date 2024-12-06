package com.eventhub.dti.infrastructure.user.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Role;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailResponseDTO implements Serializable {
  private Long id;
  private String email;
  private String phone;
  private String password;
  private String referral;
  private String profilePictureUrl;
  private String role;
  private Boolean isOnboardingFinished = false;

  public UserDetailResponseDTO(Long id, @Size(max = 100)
  @NotNull String email, @Size(max = 100) String profilePictureUrl, @NotNull Boolean isOnboardingFinished) {
  }


  public UserDetailResponseDTO(Long id, @Size(max = 100) @NotNull String email, @Size(max = 50, message = "Password must be at least 6 characters")
  @NotNull(message = "Password is mandatory") String password, @Size(max = 50) String referralCode, @Size(max = 100) String profilePictureUrl, @NotBlank(message = "Role is mandatory") String role, @NotNull Boolean isOnboardingFinished) {
  }
}
