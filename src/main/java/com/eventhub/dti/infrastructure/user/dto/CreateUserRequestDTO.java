package com.eventhub.dti.infrastructure.user.dto;

import com.eventhub.dti.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestDTO {
  private String name;
  private String email;
  private String password;
  private String profilePictureURL;
  private Boolean role;

  public User toEntity(){
    User user = new User();
    user.setName(name);
    user.setEmail(email);
    user.setPassword(password);
    user.setProfilePictureUrl(profilePictureURL);
    Set<Role> role = new HashSet<>();
    return user;
  }
}
