package com.eventhub.dti.infrastructure.discount.controller;

import com.eventhub.dti.infrastructure.discount.dto.CreateDiscountRequestDTO;
import com.eventhub.dti.infrastructure.discount.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/vi/discounts")
public class DiscountController {
  private final DiscountService discountService;

  @Autowired
  public DiscountController(DiscountService discountService) {
    this.discountService = discountService;
  }

  @GetMapping
  public ResponseEntity<List<CreateDiscountRequestDTO>> getAllDiscounts() {
    List<CreateDiscountRequestDTO> discounts = discountService.getAllDiscounts();
    return ResponseEntity.ok(discounts);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CreateDiscountRequestDTO> getDiscountById(@PathVariable Long id) {
    Optional<CreateDiscountRequestDTO> discount = discountService.getDiscountById(id);
    return discount.map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @PostMapping
  public ResponseEntity<CreateDiscountRequestDTO> createDiscount(@RequestBody CreateDiscountRequestDTO createDiscountRequestDTO) {
    CreateDiscountRequestDTO createdDiscount = discountService.createDiscount(createDiscountRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdDiscount);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CreateDiscountRequestDTO> updateDiscount(@PathVariable Long id, @RequestBody CreateDiscountRequestDTO createDiscountRequestDTO) {
    Optional<CreateDiscountRequestDTO> updatedDiscount = discountService.updateDiscount(id, createDiscountRequestDTO);
    return updatedDiscount.map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDiscount(@PathVariable Long id) {
    boolean isDeleted = discountService.deleteDiscount(id);
    return isDeleted ? ResponseEntity.noContent().build()
      : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }
}
