package com.eventhub.dti.usecase.user.impl;


import com.eventhub.dti.entity.User;
import com.eventhub.dti.infrastructure.user.dto.BulkCreateUserRequestDTO;
import com.eventhub.dti.infrastructure.user.dto.CreateUserRequestDTO;
import com.eventhub.dti.infrastructure.user.dto.UserDetailResponseDTO;
import com.eventhub.dti.infrastructure.user.repository.RoleRepository;
import com.eventhub.dti.infrastructure.user.repository.UserRepository;
import com.eventhub.dti.usecase.user.CreateUserUseCase;
import jakarta.transaction.Transactional;
import lombok.extern.java.Log;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Log
@Service
public class CreateUserUseCaseImpl implements CreateUserUseCase {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final RoleRepository roleRepository;

  public CreateUserUseCaseImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.roleRepository = roleRepository;
  }

  @Override
  @CachePut(value = "userDetailResponseDTO", key = "#result.id", unless = "#result.isOnboardingFinished == true")
  public UserDetailResponseDTO createUser(CreateUserRequestDTO req){
    User newUser = req.toEntity();
    newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

    var savedUser = userRepository.save(newUser);
    return new UserDetailResponseDTO(savedUser.getId(), savedUser.getEmail(), savedUser.getProfilePictureUrl(), savedUser.getIsOnboardingFinished());
  }

  @Override
  public User createUserWithEntity(User req) {
    return null;
  }

  @Override
  @Transactional
  public List<User> bulkCreateUser(BulkCreateUserRequestDTO req) {
    List<User> usersList = req.getUser().stream().map(CreateUserRequestDTO::toEntity).toList();
    userRepository.saveAll(usersList);
    return usersList;
  }
}
