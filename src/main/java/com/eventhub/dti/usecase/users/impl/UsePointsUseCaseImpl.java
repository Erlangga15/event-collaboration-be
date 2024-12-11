package com.eventhub.dti.usecase.users.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventhub.dti.common.exceptions.InsufficientPointsException;
import com.eventhub.dti.common.exceptions.UserNotFoundException;
import com.eventhub.dti.entity.Point;
import com.eventhub.dti.entity.User;
import com.eventhub.dti.entity.enums.PointStatus;
import com.eventhub.dti.repository.PointRepository;
import com.eventhub.dti.repository.UserRepository;
import com.eventhub.dti.usecase.users.UsePointsUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsePointsUseCaseImpl implements UsePointsUseCase {
    private final UserRepository userRepository;
    private final PointRepository pointRepository;

    private static final BigDecimal POINTS_TO_MONEY_RATIO = new BigDecimal("100"); // 1 point = Rp 100
    private static final BigDecimal MAX_DISCOUNT_PERCENTAGE = new BigDecimal("0.50"); // 50% maximum discount

    @Override
    @Transactional
    public BigDecimal execute(UUID userId, Integer points, BigDecimal ticketPrice) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        List<Point> activePoints = pointRepository.findByUserIdAndStatusOrderByExpiryDateAsc(userId,
                PointStatus.ACTIVE);
        int totalPoints = activePoints.stream().mapToInt(Point::getPoints).sum();

        if (points > totalPoints) {
            throw new InsufficientPointsException(
                    "Insufficient points. Available: " + totalPoints + ", Requested: " + points);
        }

        BigDecimal discountAmount = new BigDecimal(points).multiply(POINTS_TO_MONEY_RATIO);
        BigDecimal maxDiscount = ticketPrice.multiply(MAX_DISCOUNT_PERCENTAGE);

        if (discountAmount.compareTo(maxDiscount) > 0) {
            throw new IllegalArgumentException("Maximum discount limit exceeded. Maximum allowed: " + maxDiscount);
        }

        int remainingPoints = points;
        for (Point point : activePoints) {
            if (remainingPoints <= 0) {
                break;
            }

            if (point.getPoints() <= remainingPoints) {
                point.setStatus(PointStatus.EXPIRED);
                remainingPoints -= point.getPoints();
            } else {
                point.setPoints(point.getPoints() - remainingPoints);
                remainingPoints = 0;
            }
            pointRepository.save(point);
        }

        return ticketPrice.subtract(discountAmount);
    }
}
