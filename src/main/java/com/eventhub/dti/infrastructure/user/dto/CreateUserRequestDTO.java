package com.eventhub.dti.infrastructure.user.dto;

import com.eventhub.dti.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import javax.management.relation.Role;
import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestDTO {
    public User toEntity(){
    User user = new User();
    user.setName(user.getName());
    user.setEmail(user.getEmail());
    user.setPassword(user.getPassword());
    user.setProfilePictureUrl(user.getProfilePictureUrl());
    Set<Role> roles = new HashSet<>();
    return user;
  }

  @NotBlank(message = "Name is mandatory")
  @Size(max = 100, message = "Name can have at most 100 characters")
  private String name;

  @NotBlank(message = "Email is mandatory")
  @Email(message = "Email should be valid")
  private String email;

  @NotBlank(message = "Password is mandatory")
  @Size(min = 6, message = "Password must be at least 6 characters")
  private String password;

  @NotBlank(message = "Role is mandatory")
  private String role; // 'customer' or 'organizer'


  @Size(max = 250, message = "Photo profile URL can have at most 250 characters")
  private String ProfilePictureUrl;

  @Size(max = 20, message = "Referral code can have at most 20 characters")
  private String referralCode;

  // Optional fields for organizers
  @Size(max = 100, message = "Website can have at most 100 characters")
  private String website;

  @Size(max = 20, message = "Phone number can have at most 20 characters")
  private String phoneNumber;

  @Size(max = 255, message = "Address can have at most 255 characters")
  private String address;

}
