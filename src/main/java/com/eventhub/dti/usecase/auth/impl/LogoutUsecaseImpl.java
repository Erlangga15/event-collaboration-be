package com.eventhub.dti.usecase.auth.impl;

import org.springframework.stereotype.Service;

import com.eventhub.dti.infrastructure.dto.LogoutRequestDTO;
import com.eventhub.dti.usecase.auth.LogoutUsecase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogoutUsecaseImpl implements LogoutUsecase {
    @Override
    public Boolean logoutUser(LogoutRequestDTO request) {
        if (request.getAccessToken() == null || request.getAccessToken().isEmpty()) {
            throw new IllegalStateException("Access token is required for logout");
        }
        return Boolean.TRUE;
    }
}
