package com.eventhub.dti.infrastructure.discount.controller;

import com.eventhub.dti.common.response.ApiResponse;
import com.eventhub.dti.common.response.PaginatedResponse;
import com.eventhub.dti.common.util.PaginationUtil;
import com.eventhub.dti.entity.Discount;
import com.eventhub.dti.infrastructure.auth.Claims;
import com.eventhub.dti.infrastructure.discount.dto.CreateDiscountRequestDTO;
import com.eventhub.dti.infrastructure.discount.dto.CreateDiscountResponseDTO;
import com.eventhub.dti.infrastructure.discount.dto.DiscountSummaryDTO;
import com.eventhub.dti.infrastructure.discount.service.DiscountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/vi/discounts")
public class DiscountController {
  private final DiscountService discountService;

  @Autowired
  public DiscountController(DiscountService discountService) {
    this.discountService = discountService;
  }

  @PreAuthorize("hasRole('ORGANIZER')")
  @PostMapping("/create")
  public ResponseEntity<?> createDiscount(@Valid @RequestBody CreateDiscountRequestDTO request) {
    Long organizerId = Claims.getUserIdFromJwt();
    Discount discount = discountService.createEventDiscount(request, organizerId);
    return ApiResponse.successfulResponse("Create new voucher success", discount);
  }

  @PreAuthorize("hasRole('ORGANIZER')")
  @GetMapping("/upcoming")
  public ResponseEntity<?> getUpcomingEventDiscount(@PageableDefault(size = 10) Pageable pageable) {
    Long organizerId = Claims.getUserIdFromJwt();
    Page<DiscountSummaryDTO> discount = discountService.getUpcomingEventDiscount(organizerId, pageable);
    PaginatedResponse<DiscountSummaryDTO> paginatedDiscount = PaginationUtil.toPaginatedResponse(discount);
    return ApiResponse.successfulResponse("Get all upcoming event discount success", paginatedDiscount);
  }

  @PreAuthorize("hasRole('ORGANIZER')")
  @GetMapping("/{discountId}")
  public ResponseEntity<?> getDiscountDetails(@PathVariable Long discountId) {
    Long organizerId = Claims.getUserIdFromJwt();
    CreateDiscountResponseDTO discountDetails = discountService.getDiscountDetails(discountId, organizerId);
    return ResponseEntity.ok(discountDetails);
  }

  @GetMapping("/by-event")
  public ResponseEntity<?> getVouchersByEventId(
    @RequestParam Long eventId,
    @PageableDefault(size = 10) Pageable pageable) {
    Page<Discount> discount = discountService.getDiscountByEventId(eventId, pageable);
    PaginatedResponse<Discount> paginatedDiscount = PaginationUtil.toPaginatedResponse(discount);
    return ApiResponse.successfulResponse("Get all discount by event success", paginatedDiscount);
  }

  @PreAuthorize("hasRole('CUSTOMER')")
  @GetMapping("/my-discount")
  public ResponseEntity<?> getMyDiscount(@PageableDefault(size = 10) Pageable pageable) {
    Long customerId = Claims.getUserIdFromJwt();

    Page<DiscountSummaryDTO> discount = discountService.getDiscountForCustomer(customerId, pageable);
    PaginatedResponse<DiscountSummaryDTO> paginatedDiscount = PaginationUtil.toPaginatedResponse(discount);

    return ApiResponse.successfulResponse("Get discount success", paginatedDiscount);
  }

}
