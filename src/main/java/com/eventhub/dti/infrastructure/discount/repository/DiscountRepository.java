package com.eventhub.dti.infrastructure.discount.repository;

import com.eventhub.dti.entity.Discount;
import com.eventhub.dti.infrastructure.discount.dto.CreateDiscountResponseDTO;
import com.eventhub.dti.infrastructure.discount.dto.DiscountSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

  @Query
  Page<DiscountSummaryDTO> findUpcomingEventDiscountByOrganizer(@Param("organizerId") Long organizerId, Long pageable);
  Optional<CreateDiscountResponseDTO> findDiscountDetailsByIdAndOrganizer(@Param("discountId") Long discountId, @Param("organizerId") Long organizerId);
  Page<Discount> findByEventId(@Param("eventId") Long eventId, Pageable pageable);

  Page<Discount> findByUserId(Long customerId, Pageable pageable);
}
