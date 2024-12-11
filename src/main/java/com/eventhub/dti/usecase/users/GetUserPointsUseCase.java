package com.eventhub.dti.usecase.users;

import java.util.UUID;

import com.eventhub.dti.infrastructure.dto.UserPointsResponseDTO;

public interface GetUserPointsUseCase {
    /**
     * Gets user points information including total points and point details
     * @param userId The ID of the user
     * @return User points information
     */
    UserPointsResponseDTO execute(UUID userId);
}
