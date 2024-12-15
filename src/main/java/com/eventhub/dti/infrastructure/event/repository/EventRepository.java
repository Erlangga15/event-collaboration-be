package com.eventhub.dti.infrastructure.event.repository;


import com.eventhub.dti.entity.Event;
import com.eventhub.dti.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
  Optional<Event> findByIdAndOrganizerId(Long eventId, Long organizerId);
  Page<Event> findByLocationNot(Pageable pageable, String location);
  Page<Event> findByOrganizerId(Pageable pageable, Long organizerId);

  @Query("SELECT e FROM Event e WHERE e.dateTimeStart > :currentDateTime AND " +
    "(LOWER(e.location) = LOWER(:location) OR :location IS NULL) AND " +
    "(LOWER(e.category) = LOWER(:category) OR :category IS NULL) AND " +
    "(LOWER(e.title) LIKE LOWER(CONCAT('%', :search, '%')) OR :search IS NULL) " +
    "ORDER BY e.dateTimeStart ASC")
  Page<Event> findUpcomingEvents(Pageable pageable, LocalDateTime currentDateTime, String location, String category, String search);

  @Query("SELECT e FROM Event e WHERE e.dateTimeStart > :currentDateTime AND " +
    "(LOWER(e.location) = LOWER(:location) OR :location IS NULL) AND " +
    "(LOWER(e.category) = LOWER(:category) OR :category IS NULL) AND " +
    "(LOWER(e.title) LIKE LOWER(CONCAT('%', :search, '%')) OR :search IS NULL) " +
    "ORDER BY e.createdAt DESC")
  Page<Event> findUpcomingEventsSortedByNewest(Pageable pageable, LocalDateTime currentDateTime, String location, String category, String search);

  @Query("SELECT e FROM Event e LEFT JOIN Review r ON e.id = r.eventId WHERE e.dateTimeStart > :currentDateTime AND " +
    "(LOWER(e.location) = LOWER(:location) OR :location IS NULL) AND " +
    "(LOWER(e.category) = LOWER(:category) OR :category IS NULL) AND " +
    "(LOWER(e.title) LIKE LOWER(CONCAT('%', :search, '%')) OR :search IS NULL) " +
    "GROUP BY e.id " +
    "ORDER BY AVG(r.rating) DESC")
  Page<Event> findUpcomingEventsSortedByHighestRating(Pageable pageable, LocalDateTime currentDateTime, String location, String category, String search);

  @Query("SELECT e FROM Event e JOIN Transaction t ON e.id = t.event.id WHERE t.customer.id = :customerId AND e.dateTimeEnd < :currentDateTime")
  Page<Event> findPastEventsByCustomer(Long customerId, LocalDateTime currentDateTime, Pageable pageable);

  @Query("SELECT e FROM Event e JOIN Transaction t ON e.id = t.event.id WHERE t.customer.id = :customerId AND e.dateTimeEnd >= :currentDateTime")
  Page<Event> findUpcomingEventsByCustomer(Long customerId, LocalDateTime currentDateTime, Pageable pageable);

}
