package com.eventhub.dti.usecase.users.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.eventhub.dti.entity.User;
import com.eventhub.dti.infrastructure.dto.UserDetailsResponseDTO;
import com.eventhub.dti.repository.UserRepository;
import com.eventhub.dti.usecase.users.GetUserDetailsUseCase;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetUserDetailsUseCaseImpl implements GetUserDetailsUseCase {

    private final UserRepository userRepository;

    @Override
    public UserDetailsResponseDTO getUserDetails(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        return UserDetailsResponseDTO.fromEntity(user);
    }
}
