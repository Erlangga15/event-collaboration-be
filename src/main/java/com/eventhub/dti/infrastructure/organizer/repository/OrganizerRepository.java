package com.eventhub.dti.infrastructure.organizer.repository;


import com.eventhub.dti.entity.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
  Optional<Organizer> findByEmail(String email);
}
