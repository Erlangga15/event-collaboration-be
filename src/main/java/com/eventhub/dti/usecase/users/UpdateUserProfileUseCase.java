package com.eventhub.dti.usecase.users;

import java.util.UUID;

import com.eventhub.dti.entity.User;
import com.eventhub.dti.infrastructure.dto.UserUpdateRequestDTO;

public interface UpdateUserProfileUseCase {
    /**
     * Updates user profile information
     * 
     * @param userId  The ID of the user to update
     * @param request Updated user profile information
     * @return Updated user entity
     */
    User execute(UUID userId, UserUpdateRequestDTO request);
}
