package com.eventhub.dti.infrastructure.user.service;

import com.eventhub.dti.entity.User;
import com.eventhub.dti.infrastructure.discount.service.DiscountService;
import com.eventhub.dti.infrastructure.point.service.PointService;
import com.eventhub.dti.infrastructure.user.repository.UserRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

@Service
public class ReferralService {
  private final UserRepository userRepository;
  private final DiscountService discountService;
  private final PointService pointService;

  public ReferralService(UserRepository userRepository, DiscountService discountService, PointService pointService) {
    this.userRepository = userRepository;
    this.discountService = discountService;
    this.pointService = pointService;
  }

  public void handleReferral(String referralCode, User referredUser) {
    User referrer = userRepository.findByReferralCode(referralCode)
      .orElseThrow(() -> new IllegalArgumentException("Invalid referral code"));

    // Generate a 10% discount voucher for the referred user with a 3-month expiration
    discountService.createIndividualDiscount(referredUser, 10);

    // Add 10000 points to the referrer with a 3-month expiration
    pointService.addPointsToReferrer(referrer, 10000);
  }
}
