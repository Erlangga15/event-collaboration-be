package com.eventhub.dti.infrastructure.discount.service;

import com.eventhub.dti.entity.Discount;
import com.eventhub.dti.entity.Event;
import com.eventhub.dti.entity.User;
import com.eventhub.dti.infrastructure.discount.dto.CreateDiscountRequestDTO;
import com.eventhub.dti.infrastructure.discount.dto.CreateDiscountResponseDTO;
import com.eventhub.dti.infrastructure.discount.dto.DiscountSummaryDTO;
import com.eventhub.dti.infrastructure.discount.repository.DiscountRepository;
import com.eventhub.dti.infrastructure.event.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountService {
  private final DiscountRepository discountRepository;
  private final EventRepository eventRepository;
  private final CreateDiscountResponseDTO createDiscountResponseDTO;

  @Autowired
  public DiscountService(DiscountRepository discountRepository, EventRepository eventRepository, CreateDiscountResponseDTO createDiscountResponseDTO) {
    this.discountRepository = discountRepository;
    this.eventRepository = eventRepository;
    this.createDiscountResponseDTO = createDiscountResponseDTO;
  }

  public Discount createEventDiscount(CreateDiscountRequestDTO request, Long organizerId) {
    Event event = (Event) eventRepository.findByIdAndOrganizerId(request.getEventId(), organizerId)
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

  public Page<DiscountSummaryDTO> getUpcomingEventVouchers(Long organizerId, Pageable pageable) {
    return discountRepository.findUpcomingEventVouchersByOrganizer(organizerId, pageable);
  }

  public CreateDiscountResponseDTO getCreateDiscountResponseDTO(Long voucherId, Long organizerId) {
    return discountRepository.findVoucherDetailsByIdAndOrganizer(voucherId, organizerId)
      .orElseThrow(() -> new IllegalArgumentException("Voucher not found or not owned by organizer"));
  }

  public Page<Discount> getDiscountsByEventId(Long eventId, Pageable pageable) {
    return discountRepository.findByEventId(eventId, pageable);
  }
}
