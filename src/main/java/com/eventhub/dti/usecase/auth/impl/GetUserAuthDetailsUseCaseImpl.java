package com.eventhub.dti.usecase.auth.impl;

import com.eventhub.dti.common.exception.DataNotFoundException;
import com.eventhub.dti.entity.User;
import com.eventhub.dti.infrastructure.auth.dto.UserAuth;
import com.eventhub.dti.infrastructure.user.repository.UserRepository;
import com.eventhub.dti.usecase.auth.GetUserAuthDetailsUseCase;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class GetUserAuthDetailsUseCaseImpl implements GetUserAuthDetailsUseCase {
  private final UserRepository usersRepository;

  public GetUserAuthDetailsUseCaseImpl(UserRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User existingUser = usersRepository.findByEmailContainsIgnoreCase(username).orElseThrow(() -> new DataNotFoundException("User not found with email: " + username));

    UserAuth userAuth = new UserAuth();
    userAuth.setUser(existingUser);
    return userAuth;
  }
}
