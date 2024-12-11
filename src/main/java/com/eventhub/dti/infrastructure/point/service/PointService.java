package com.eventhub.dti.infrastructure.point.service;

import com.eventhub.dti.entity.Point;
import com.eventhub.dti.entity.User;
import com.eventhub.dti.infrastructure.point.dto.CreatePointRequestDTO;
import com.eventhub.dti.infrastructure.point.dto.MapperPointDTO;
import com.eventhub.dti.infrastructure.point.repository.PointRepository;
import com.eventhub.dti.infrastructure.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PointService {
  private final PointRepository pointRepository;
  private final MapperPointDTO mapperPointDTO;
  private final UserRepository userRepository;

  @Autowired
  public PointService(PointRepository pointRepository, MapperPointDTO mapperPointDTO, UserRepository userRepository) {
    this.pointRepository = pointRepository;
    this.mapperPointDTO = mapperPointDTO;
    this.userRepository = userRepository;
  }

  public List<CreatePointRequestDTO> getAllPoints() {
    List<Point> points = pointRepository.findAll();
    return points.stream()
      .map(mapperPointDTO::toDTO)
      .toList();
  }

  public Optional<CreatePointRequestDTO> getPointById(Long id) {
    Optional<Point> point = pointRepository.findById(id);
    return point.map(mapperPointDTO::toDTO);
  }

  public Optional<CreatePointRequestDTO> getPointByReferralCode(String referralCode) {
    Optional<Point> point = pointRepository.findByReferralCode(referralCode);
    return point.map(mapperPointDTO::toDTO);
  }

  // Create New Point
  public CreatePointRequestDTO createPoint(CreatePointRequestDTO createPointRequestDTO) {
    Point point = mapperPointDTO.toEntity(createPointRequestDTO);
    point = pointRepository.save(point);
    return mapperPointDTO.toDTO(point);
  }

  public Optional<CreatePointRequestDTO> updatePoint(Long id, CreatePointRequestDTO createPointRequestDTO) {
    if (pointRepository.existsById(id)) {
      createPointRequestDTO.setId(id);
      Point point = mapperPointDTO.toEntity(createPointRequestDTO);
      point = pointRepository.save(point);
      return Optional.of(mapperPointDTO.toDTO(point));
    }
    return Optional.empty();
  }

  public boolean deletePoint(Long id) {
    if (pointRepository.existsById(id)) {
      pointRepository.deleteById(id);
      return true;
    }
    return false;
  }

  @Transactional
  public void checkAndUpdateExpiredPoints() {
    OffsetDateTime now = OffsetDateTime.now();
    List<Point> expiredPoints = pointRepository.findAllByExpiredAtBeforeAndRemainingGreaterThanAndIsDeductedIsFalse(now, BigDecimal.ZERO);
    for (Point point : expiredPoints) {
      User user = point.getUser();
      BigDecimal expiredAmount = point.getRemaining();
      user.setPoints(user.getPoints().subtract(expiredAmount));
      point.setIsDeducted(true);
      pointRepository.save(point);
      userRepository.save(user);
    }
  }
}
