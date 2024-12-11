package com.eventhub.dti.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventhub.dti.entity.ReferralUsage;

@Repository
public interface ReferralUsageRepository extends JpaRepository<ReferralUsage, UUID> {
}
