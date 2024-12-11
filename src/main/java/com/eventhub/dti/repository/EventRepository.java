package com.eventhub.dti.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eventhub.dti.entity.Event;
import com.eventhub.dti.entity.User;
import com.eventhub.dti.entity.enums.EventCategory;
import com.eventhub.dti.entity.enums.EventStatus;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID>, JpaSpecificationExecutor<Event> {

    Page<Event> findByOrganizer(User organizer, Pageable pageable);

    Page<Event> findByStatusNotInAndCategory(List<EventStatus> excludedStatuses, EventCategory category,
            Pageable pageable);

    @Query("SELECT e FROM Event e WHERE e.startDate > :now AND e.status = :status ORDER BY e.startDate ASC")
    Page<Event> findUpcomingEvents(LocalDateTime now, EventStatus status, Pageable pageable);

    Page<Event> findByVenueNameContainingIgnoreCase(String venueName, Pageable pageable);

    Page<Event> findByStartDateBetweenAndStatus(LocalDateTime startDate, LocalDateTime endDate, EventStatus status,
            Pageable pageable);

    boolean existsByOrganizer(User organizer);

    long countByStatusAndCategory(EventStatus status, EventCategory category);

    @EntityGraph(attributePaths = { "tickets", "organizer" })
    Optional<Event> findById(UUID id);

    @EntityGraph(attributePaths = { "tickets", "organizer" })
    Page<Event> findAll(Specification<Event> spec, Pageable pageable);
}
