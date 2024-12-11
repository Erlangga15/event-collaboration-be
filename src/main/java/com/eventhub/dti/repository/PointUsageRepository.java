package com.eventhub.dti.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventhub.dti.entity.PointUsage;

@Repository
public interface PointUsageRepository extends JpaRepository<PointUsage, UUID> {
}
