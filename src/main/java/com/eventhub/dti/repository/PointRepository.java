package com.eventhub.dti.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventhub.dti.entity.Point;
import com.eventhub.dti.entity.enums.PointStatus;

@Repository
public interface PointRepository extends JpaRepository<Point, UUID> {
    List<Point> findByUserIdAndStatusOrderByExpiryDateAsc(UUID userId, PointStatus status);
}
