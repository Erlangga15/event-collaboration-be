package com.eventhub.dti.usecase.user.impl;

import com.eventhub.dti.common.exception.DataNotFoundException;
import com.eventhub.dti.entity.User;
import com.eventhub.dti.infrastructure.user.dto.UserDetailResponseDTO;
import com.eventhub.dti.infrastructure.user.repository.UserRepository;
import com.eventhub.dti.usecase.user.GetUserUseCase;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetUserUseCaseImpl implements GetUserUseCase {
  private final UserRepository userRepository;

  public GetUserUseCaseImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<User> getAllUsers() {
    return List.of();
  }

  @Override
  public List<User> getAllUser() {
    return userRepository.findAll();
  }

  @Override
  @Cacheable(value = "userDetailResponseDTO", key = "#id", unless = "#result.isOnboardingFinished == true")
  public UserDetailResponseDTO getUserById(Long id) {
    var foundUser = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("User not found"));
    return new UserDetailResponseDTO();
  }
}
