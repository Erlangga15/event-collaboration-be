package com.eventhub.dti.usecase.auth.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eventhub.dti.entity.User;
import com.eventhub.dti.infrastructure.security.UserAuth;
import com.eventhub.dti.repository.UserRepository;
import com.eventhub.dti.usecase.auth.GetUserAuthDetailsUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetUserAuthDetailsUseCaseImpl implements GetUserAuthDetailsUseCase {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        UserAuth userAuth = new UserAuth();
        userAuth.setUser(user);
        return userAuth;
    }
}
