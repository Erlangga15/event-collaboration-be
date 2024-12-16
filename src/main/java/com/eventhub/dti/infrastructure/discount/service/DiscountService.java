package com.eventhub.dti.infrastructure.discount.service;


import com.eventhub.dti.entity.*;
import com.eventhub.dti.infrastructure.discount.dto.CreateDiscountRequestDTO;
import com.eventhub.dti.infrastructure.discount.dto.CreateDiscountResponseDTO;
import com.eventhub.dti.infrastructure.discount.dto.DiscountSummaryDTO;
import com.eventhub.dti.infrastructure.discount.repository.DiscountRepository;
import com.eventhub.dti.infrastructure.event.repository.EventRepository;
import com.eventhub.dti.infrastructure.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class DiscountService {
  private final DiscountRepository discountRepository;
  private final EventRepository eventRepository;
  private final CreateDiscountResponseDTO createDiscountResponseDTO;
  private final UserRepository userRepository;

  public DiscountService(DiscountRepository discountRepository, EventRepository eventRepository, CreateDiscountResponseDTO createDiscountResponseDTO, UserRepository userRepository) {
    this.discountRepository = discountRepository;
    this.eventRepository = eventRepository;
    this.createDiscountResponseDTO = createDiscountResponseDTO;
    this.userRepository = userRepository;
  }

  public Discount createEventDiscount(CreateDiscountRequestDTO request, Long organizerId) {
    Event event = eventRepository.findByIdAndOrganizerId(request.getEventId(), organizerId)
      .orElseThrow(() -> new IllegalArgumentException("Event not found or not owned by organizer"));

    Discount discount = new Discount();
    discount.setEvent(event);
    discount.setDiscountCode(request.getDiscountCode());
    discount.setDiscountValue(request.getDiscountValue());
    discount.setDescription(request.getDescription());
    discount.setDiscountType(request.getDiscountType());
    discount = discountRepository.save(discount);

    return discount;
  }

  public void createIndividualDiscount(User user, int discountPercentage) {
    Discount discount = new Discount();
    discount.setUser(user);
    discount.setDiscountValue(BigDecimal.valueOf(discountPercentage));
    discount.setDiscountType("DISCOUNT");
    discount.setDescription("10% discount voucher for using referral code");
    discount.setExpiresAt(LocalDateTime.now().plusMonths(3));

    discountRepository.save(discount);
  }

  public Page<DiscountSummaryDTO> getUpcomingEventDiscount (Long organizerId, Pageable pageable) {
    return discountRepository.findUpcomingEventDiscountByOrganizer(organizerId, pageable.getOffset());
  }

  public CreateDiscountResponseDTO getDiscountDetails(Long voucherId, Long organizerId) {
    return discountRepository.findDiscountDetailsByIdAndOrganizer(voucherId, organizerId)
      .orElseThrow(() -> new IllegalArgumentException("Discount not found or not owned by organizer"));
  }

  public Page<Discount> getDiscountByEventId(Long eventId, Pageable pageable) {
    return discountRepository.findByEventId(eventId, pageable);
  }

  public Page<DiscountSummaryDTO> getDiscountForCustomer(Long customerId, Pageable pageable) {
    User customer = userRepository.findById(customerId)
      .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

    Page<Discount> discount = discountRepository.findByUserId(customerId, pageable);

    return discount.map(voucher -> {
      DiscountSummaryDTO dto = new DiscountSummaryDTO();
      dto.setDiscountId(createDiscountResponseDTO.getVoucherId());
      dto.setDiscountCode(createDiscountResponseDTO.getDiscountCode());
      dto.setEventName(createDiscountResponseDTO.getEventName());
      dto.setStatus(createDiscountResponseDTO.getStatus());
      return dto;
    });
  }
}
