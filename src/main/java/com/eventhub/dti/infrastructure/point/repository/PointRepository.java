package com.eventhub.dti.infrastructure.point.repository;

import com.eventhub.dti.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {
  Optional<Point> findByReferralCode(String referralCode);
  List<Point> findAllByExpiredAtBeforeAndRemainingGreaterThanAndIsDeductedIsFalse(OffsetDateTime expiredAt, BigDecimal remaining);
}
