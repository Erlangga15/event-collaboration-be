package com.eventhub.dti.infrastructure.review.repository;

import com.eventhub.dti.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
  Page<Review> findByEventId(Long eventId, Pageable pageable);
}
