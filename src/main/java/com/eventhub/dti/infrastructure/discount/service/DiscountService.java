package com.eventhub.dti.infrastructure.discount.service;

import com.eventhub.dti.entity.Discount;
import com.eventhub.dti.infrastructure.discount.dto.CreateDiscountRequestDTO;
import com.eventhub.dti.infrastructure.discount.dto.CreateDiscountResponseDTO;
import com.eventhub.dti.infrastructure.discount.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscountService {
  private final DiscountRepository discountRepository;
  private final CreateDiscountResponseDTO createDiscountResponseDTO;

  @Autowired
  public DiscountService(DiscountRepository discountRepository, CreateDiscountResponseDTO createDiscountResponseDTO) {
    this.discountRepository = discountRepository;
    this.createDiscountResponseDTO = createDiscountResponseDTO;
  }

  public List<CreateDiscountRequestDTO> getAllDiscounts() {
    List<Discount> discounts = discountRepository.findAll();
    return discounts.stream()
      .map(createDiscountResponseDTO::toDTO)
      .toList();
  }

  public Optional<CreateDiscountRequestDTO> getDiscountById(Long id) {
    Optional<Discount> discount = discountRepository.findById(id);
    return discount.map(createDiscountResponseDTO::toDTO);
  }

  public CreateDiscountRequestDTO createDiscount(CreateDiscountRequestDTO createDiscountRequestDTO) {
    Discount discount = createDiscountResponseDTO.toEntity(createDiscountRequestDTO);
    discount = discountRepository.save(discount);
    return createDiscountResponseDTO.toDTO(discount);
  }

  public Optional<CreateDiscountRequestDTO> updateDiscount(Long id, CreateDiscountRequestDTO createDiscountRequestDTO) {
    if (discountRepository.existsById(id)) {
      createDiscountRequestDTO.setId(id);
      Discount discount = createDiscountResponseDTO.toEntity(createDiscountRequestDTO);
      discount = discountRepository.save(discount);
      return Optional.of(createDiscountResponseDTO.toDTO(discount));
    }
    return Optional.empty();
  }

  public boolean deleteDiscount(Long id) {
    if (discountRepository.existsById(id)) {
      discountRepository.deleteById(id);
      return true;
    }
    return false;
  }
}
