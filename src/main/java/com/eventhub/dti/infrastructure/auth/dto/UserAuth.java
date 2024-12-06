package com.eventhub.dti.infrastructure.auth.dto;

import com.eventhub.dti.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuth implements UserDetails {
  private User user;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities(){
      return new ArrayList<GrantedAuthority>();
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
