package com.eventhub.dti.usecase.auth;

import com.eventhub.dti.infrastructure.dto.LogoutRequestDTO;

public interface LogoutUsecase {
    Boolean logoutUser(LogoutRequestDTO request);
}
