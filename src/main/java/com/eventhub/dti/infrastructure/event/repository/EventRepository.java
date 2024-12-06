package com.eventhub.dti.infrastructure.event.repository;


import com.eventhub.dti.entity.Event;
import com.eventhub.dti.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
  Optional<Event> findByIdAndOrganizer(Long eventId, User organizer);
}
