package com.eventhub.dti.usecase.users.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventhub.dti.entity.User;
import com.eventhub.dti.infrastructure.dto.UserUpdateRequestDTO;
import com.eventhub.dti.repository.UserRepository;
import com.eventhub.dti.usecase.users.UpdateUserProfileUseCase;
import com.eventhub.dti.common.exceptions.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateUserProfileUseCaseImpl implements UpdateUserProfileUseCase {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public User execute(UUID userId, UserUpdateRequestDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        if (!user.getPhone().equals(request.getPhone()) &&
                userRepository.existsByPhone(request.getPhone())) {
            throw new IllegalArgumentException("Phone number already registered");
        }

        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        
        return userRepository.save(user);
    }
}
