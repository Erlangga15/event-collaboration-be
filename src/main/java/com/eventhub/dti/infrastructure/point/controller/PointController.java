package com.eventhub.dti.infrastructure.point.controller;

import com.eventhub.dti.infrastructure.point.dto.CreatePointRequestDTO;
import com.eventhub.dti.infrastructure.point.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/points")
public class PointController {
  private final PointService pointService;

  @Autowired
  public PointController(PointService pointService) {
    this.pointService = pointService;
  }

  @GetMapping
  public ResponseEntity<List<CreatePointRequestDTO>> getAllPoints() {
    List<CreatePointRequestDTO> points = pointService.getAllPoints();
    return ResponseEntity.ok(points);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CreatePointRequestDTO> getPointById(@PathVariable Long id) {
    Optional<CreatePointRequestDTO> point = pointService.getPointById(id);
    return point.map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @GetMapping("/referral/{referralCode}")
  public ResponseEntity<CreatePointRequestDTO> getPointByReferralCode(@PathVariable String referralCode) {
    Optional<CreatePointRequestDTO> point = pointService.getPointByReferralCode(referralCode);
    return point.map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  // Create New Point
  @PostMapping
  public ResponseEntity<CreatePointRequestDTO> createPoint(@RequestBody CreatePointRequestDTO createPointRequestDTO) {
    CreatePointRequestDTO createdPoint = pointService.createPoint(createPointRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdPoint);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CreatePointRequestDTO> updatePoint(@PathVariable Long id, @RequestBody CreatePointRequestDTO createPointRequestDTO) {
    Optional<CreatePointRequestDTO> updatedPoint = pointService.updatePoint(id, createPointRequestDTO);
    return updatedPoint.map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePoint(@PathVariable Long id) {
    boolean isDeleted = pointService.deletePoint(id);
    return isDeleted ? ResponseEntity.noContent().build()
      : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @GetMapping("/invalidate-expired")
  public void invalidateExpiredPoints() {
    pointService.checkAndUpdateExpiredPoints();
  }
}
