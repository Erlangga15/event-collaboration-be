package com.eventhub.dti.usecase.users;

import java.util.UUID;

import com.eventhub.dti.infrastructure.dto.UserDetailsResponseDTO;

public interface GetUserDetailsUseCase {
    UserDetailsResponseDTO getUserDetails(UUID userId);
}
