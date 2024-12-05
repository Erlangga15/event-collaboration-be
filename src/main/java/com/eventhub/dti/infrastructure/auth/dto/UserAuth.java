package com.eventhub.dti.infrastructure.config;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuth implements UserDetails {
  private User user;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities(){
    return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
  }

  @Override
  public String getUsername() {
    return this.user.getEmail();
  }

  @Override
  public String getPassword() {
    return this.user.getPassword();
  }

  @Override
  public boolean isAccountNonExpired() {
    return UserDetails.super.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return UserDetails.super.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return UserDetails.super.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    return UserDetails.super.isEnabled();
  }
}
