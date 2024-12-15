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
  @Query("SELECT new com.vibetribe.backend.infrastructure.usecase.voucher.dto.VoucherSummaryDTO(v.id, e.title, v.voucherCode, " +
    "CASE WHEN v.voucherType = 'quantity' AND qbv.quantityLimit = qbv.quantityUsed THEN 'not available' " +
    "WHEN v.voucherType = 'dateRange' AND v.expiresAt < CURRENT_TIMESTAMP THEN 'not available' " +
    "ELSE 'available' END) " +
    "FROM Voucher v " +
    "JOIN v.event e " +
    "LEFT JOIN v.quantityBasedVoucher qbv " +
    "WHERE e.organizer.id = :organizerId AND e.dateTimeStart > CURRENT_TIMESTAMP")
  Page<DiscountSummaryDTO> findUpcomingEventVouchersByOrganizer(@Param("organizerId") Long organizerId, Pageable pageable);

  @Query("SELECT new com.vibetribe.backend.infrastructure.usecase.voucher.dto.VoucherDetailsDTO(v.id, e.title, v.description, v.voucherValue, v.voucherCode, v.voucherType, " +
    "CASE WHEN v.voucherType = 'quantity' AND qbv.quantityLimit = qbv.quantityUsed THEN 'not available' " +
    "WHEN v.voucherType = 'dateRange' AND v.expiresAt < CURRENT_TIMESTAMP THEN 'not available' " +
    "ELSE 'available' END, qbv.quantityLimit - qbv.quantityUsed, drv.startDate, drv.endDate) " +
    "FROM Voucher v " +
    "JOIN v.event e " +
    "LEFT JOIN v.quantityBasedVoucher qbv " +
    "LEFT JOIN v.dateRangeBasedVoucher drv " +
    "WHERE v.id = :voucherId AND e.organizer.id = :organizerId")
  Optional<CreateDiscountResponseDTO> findVoucherDetailsByIdAndOrganizer(@Param("discountId") Long discountId, @Param("organizerId") Long organizerId);

  //    @Query("SELECT v FROM Voucher v WHERE v.event.id = :eventId")
  Page<Discount> findByEventId(@Param("eventId") Long eventId, Pageable pageable);
}
