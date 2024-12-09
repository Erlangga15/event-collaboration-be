package com.eventhub.dti.infrastructure.ticket.repository;


import com.eventhub.dti.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
  @Query("select t from Ticket t where t.user.id=:userId and t.event.startDate > :date")
  Page<Ticket> findByUserIdAndDateBefore(@Param("userId") Long userId, @Param("date") LocalDateTime dateTime, Pageable pageable);

  Page<Ticket> findByUserId(Long userId, Pageable pageable);
}
