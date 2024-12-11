package com.eventhub.dti.usecase.users.impl;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventhub.dti.common.exceptions.DuplicateEmailException;
import com.eventhub.dti.common.exceptions.DuplicatePhoneException;
import com.eventhub.dti.common.exceptions.InvalidReferralCodeException;
import com.eventhub.dti.entity.Point;
import com.eventhub.dti.entity.ReferralUsage;
import com.eventhub.dti.entity.User;
import com.eventhub.dti.entity.enums.PointStatus;
import com.eventhub.dti.entity.enums.TokenType;
import com.eventhub.dti.entity.enums.UserStatus;
import com.eventhub.dti.infrastructure.dto.LoginResponseDTO;
import com.eventhub.dti.infrastructure.dto.UserRegisterRequestDTO;
import com.eventhub.dti.repository.PointRepository;
import com.eventhub.dti.repository.ReferralUsageRepository;
import com.eventhub.dti.repository.UserRepository;
import com.eventhub.dti.usecase.auth.TokenGenerationUsecase;
import com.eventhub.dti.usecase.users.RegisterUserUseCase;
import com.eventhub.dti.utils.ReferralCodeGenerator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterUserUseCaseImpl implements RegisterUserUseCase {
    private final UserRepository userRepository;
    private final PointRepository pointRepository;
    private final ReferralUsageRepository referralUsageRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReferralCodeGenerator referralCodeGenerator;
    private final AuthenticationManager authenticationManager;
    private final TokenGenerationUsecase tokenService;

    @Override
    @Transactional
    public LoginResponseDTO registerUser(UserRegisterRequestDTO request, String referralCode) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Email already registered: " + request.getEmail());
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new DuplicatePhoneException("Phone number already registered: " + request.getPhone());
        }

        User referrer = null;
        if (referralCode != null && !referralCode.isEmpty()) {
            referrer = userRepository.findByReferralCode(referralCode)
                    .orElseThrow(() -> new InvalidReferralCodeException("Invalid referral code: " + referralCode));
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());
        user.setStatus(UserStatus.ACTIVE);
        user.setReferralCode(referralCodeGenerator.generate());
        user = userRepository.save(user);

        if (referrer != null) {
            ReferralUsage referralUsage = new ReferralUsage();
            referralUsage.setReferrer(referrer);
            referralUsage.setUser(user);
            referralUsage.setCode(referralCode);
            referralUsageRepository.save(referralUsage);

            Point point = new Point();
            point.setUser(referrer);
            point.setPoints(10000);
            point.setExpiryDate(LocalDateTime.now().plusMonths(3));
            point.setStatus(PointStatus.ACTIVE);
            pointRepository.save(point);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        String accessToken = tokenService.generateToken(authentication, TokenType.ACCESS);
        String refreshToken = tokenService.generateToken(authentication, TokenType.REFRESH);

        return LoginResponseDTO.fromUserAndTokens(user, accessToken, refreshToken);
    }
}
