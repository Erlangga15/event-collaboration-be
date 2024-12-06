package com.eventhub.dti.infrastructure.location.repository;

import com.eventhub.dti.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
