package com.eventhub.dti.usecase.users.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventhub.dti.entity.Point;
import com.eventhub.dti.entity.ReferralUsage;
import com.eventhub.dti.entity.User;
import com.eventhub.dti.entity.enums.PointStatus;
import com.eventhub.dti.repository.PointRepository;
import com.eventhub.dti.repository.ReferralUsageRepository;
import com.eventhub.dti.repository.UserRepository;
import com.eventhub.dti.usecase.users.UseReferralCodeUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UseReferralCodeUseCaseImpl implements UseReferralCodeUseCase {
    private static final int REFERRAL_POINTS = 10000;
    private static final int POINTS_EXPIRY_MONTHS = 3;

    private final UserRepository userRepository;
    private final PointRepository pointRepository;
    private final ReferralUsageRepository referralUsageRepository;

    @Override
    @Transactional
    public boolean execute(String referralCode, UUID userId) {
        User referrer = userRepository.findByReferralCode(referralCode)
                .orElseThrow(() -> new IllegalArgumentException("Invalid referral code"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (referrer.getId().equals(userId)) {
            throw new IllegalArgumentException("Cannot use own referral code");
        }

        ReferralUsage referralUsage = new ReferralUsage();
        referralUsage.setReferrer(referrer);
        referralUsage.setUser(user);
        referralUsageRepository.save(referralUsage);

        Point point = new Point();
        point.setUser(referrer);
        point.setPoints(REFERRAL_POINTS);
        point.setStatus(PointStatus.ACTIVE);
        point.setExpiryDate(LocalDateTime.now().plusMonths(POINTS_EXPIRY_MONTHS));
        pointRepository.save(point);

        return true;
    }
}
