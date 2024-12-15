package com.eventhub.dti.infrastructure.user.service;

import com.eventhub.dti.common.response.PaginatedResponse;
import com.eventhub.dti.common.util.PaginationUtil;
import com.eventhub.dti.common.util.ReferralCodeGenerator;
import com.eventhub.dti.entity.Event;
import com.eventhub.dti.entity.User;
import com.eventhub.dti.infrastructure.event.repository.EventRepository;
import com.eventhub.dti.infrastructure.user.dto.CreateUserRequestDTO;
import com.eventhub.dti.infrastructure.user.dto.UpdateUserDTO;
import com.eventhub.dti.infrastructure.user.dto.UserDetailResponseDTO;
import com.eventhub.dti.infrastructure.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final ReferralService referralService;
  private final EventRepository eventRepository;

  private static final String DEFAULT_PROFILE_ICON_URL = "https://img.icons8.com/?size=100&id=tZuAOUGm9AuS&format=png&color=000000";

  public UserService(UserRepository userRepository,
                     PasswordEncoder passwordEncoder,
                     ReferralService referralService,
                     EventRepository eventRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.referralService = referralService;
    this.eventRepository = eventRepository;
  }

  @Transactional
  public User createUser(CreateUserRequestDTO createUserRequestDTO) {
    validateRole(createUserRequestDTO.getRole());

    User user = mapToUserEntity(createUserRequestDTO);
    User savedUser = userRepository.save(user);

    // Handle referral code if provided
    if (createUserRequestDTO.getReferralCode() != null) {
      referralService.handleReferral(createUserRequestDTO.getReferralCode(), savedUser);
    }

    return savedUser;
  }

  private void validateRole(String role) {
    if (!role.equalsIgnoreCase("customer") && !role.equalsIgnoreCase("organizer")) {
      throw new IllegalArgumentException("Invalid role specified");
    }
  }

  private User mapToUserEntity(CreateUserRequestDTO createUserRequestDTO) {
    User user = new User();
    user.setName(createUserRequestDTO.getName());
    user.setEmail(createUserRequestDTO.getEmail());
    user.setPassword(passwordEncoder.encode(createUserRequestDTO.getPassword()));
    user.setRoles(createUserRequestDTO.toEntity().getRoles());
    user.setReferralCode(ReferralCodeGenerator.generateReferralCode(createUserRequestDTO.getEmail()));
    user.setProfilePictureUrl(createUserRequestDTO.getProfilePictureUrl() != null ?
      createUserRequestDTO.getProfilePictureUrl() : DEFAULT_PROFILE_ICON_URL);

    // Set additional fields for organizer
    if ("organizer".equalsIgnoreCase(createUserRequestDTO.getRole())) {
      user.setReferralCode(null);

      if (createUserRequestDTO.getWebsite() == null) {
        throw new IllegalArgumentException("Website is mandatory for organizer");
      }

      if (createUserRequestDTO.getPhoneNumber() == null) {
        throw new IllegalArgumentException("Phone number is mandatory for organizer");
      }

      if (createUserRequestDTO.getAddress() == null) {
        throw new IllegalArgumentException("Address is mandatory for organizer");
      }

      user.setWebsite(createUserRequestDTO.getWebsite());
      user.setPhoneNumber(createUserRequestDTO.getPhoneNumber());
    }

    return user;

  }
  public User getUserById(Long id) {
    return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
  }

  public User updateUser(Long userId, UpdateUserDTO updateUserDTO) {
    User user = userRepository.findById(userId)
      .orElseThrow(() -> new RuntimeException("User not found"));

    if (updateUserDTO.getName() != null && !updateUserDTO.getName().isEmpty()) {
      user.setName(updateUserDTO.getName());
    }

    if (updateUserDTO.getEmail() != null && !updateUserDTO.getEmail().isEmpty()) {
      if (userRepository.existsByEmail(updateUserDTO.getEmail())) {
        throw new RuntimeException("Email is already in use");
      }
      user.setEmail(updateUserDTO.getEmail());
    }

    if (updateUserDTO.getPassword() != null && !updateUserDTO.getPassword().isEmpty()) {
      user.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
    }

    user.setProfilePictureUrl(updateUserDTO.getPhotoProfileUrl());
    user.setWebsite(updateUserDTO.getWebsite());
    user.setPhoneNumber(updateUserDTO.getPhoneNumber());

    return userRepository.save(user);
  }

  public UserDetailResponseDTO getUserDetails(Long userId, Pageable pageable) {
    User user = userRepository.findById(userId)
      .orElseThrow(() -> new IllegalArgumentException("User not found"));

    UserDetailResponseDTO userDetailResponseDTO = new UserDetailResponseDTO();
    userDetailResponseDTO.setUserId(user.getId());
    userDetailResponseDTO.setPhotoProfileUrl(user.getProfilePictureUrl());
    userDetailResponseDTO.setFullName(user.getName());
    userDetailResponseDTO.setEmail(user.getEmail());

    if (user.getRoles().equals("ORGANIZER")) {
      userDetailResponseDTO.setWebsite(user.getWebsite());
      Pageable page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
      Page<Event> events = eventRepository.findByOrganizerId(pageable, userId);
      PaginatedResponse<Event> paginatedEvents = PaginationUtil.toPaginatedResponse(events);
      userDetailResponseDTO.setEvents(paginatedEvents);
    }

    return userDetailResponseDTO;
  }
}
