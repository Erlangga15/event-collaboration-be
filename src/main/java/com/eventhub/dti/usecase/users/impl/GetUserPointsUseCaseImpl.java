package com.eventhub.dti.usecase.users.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventhub.dti.entity.Point;
import com.eventhub.dti.entity.enums.PointStatus;
import com.eventhub.dti.infrastructure.dto.UserPointsResponseDTO;
import com.eventhub.dti.repository.PointRepository;
import com.eventhub.dti.usecase.users.GetUserPointsUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetUserPointsUseCaseImpl implements GetUserPointsUseCase {
    private final PointRepository pointRepository;

    @Override
    @Transactional(readOnly = true)
    public UserPointsResponseDTO execute(UUID userId) {
        List<Point> activePoints = pointRepository.findByUserIdAndStatusOrderByExpiryDateAsc(userId, PointStatus.ACTIVE);
        
        UserPointsResponseDTO response = new UserPointsResponseDTO();
        response.setUserId(userId);
        response.setTotalPoints(calculateTotalPoints(activePoints));
        response.setPointDetails(mapToPointDetails(activePoints));
        
        return response;
    }

    private int calculateTotalPoints(List<Point> points) {
        return points.stream()
                .mapToInt(Point::getPoints)
                .sum();
    }

    private List<UserPointsResponseDTO.PointDetailDTO> mapToPointDetails(List<Point> points) {
        return points.stream()
                .map(point -> {
                    UserPointsResponseDTO.PointDetailDTO detail = new UserPointsResponseDTO.PointDetailDTO();
                    detail.setPointId(point.getId());
                    detail.setPoints(point.getPoints());
                    detail.setExpiryDate(point.getExpiryDate());
                    detail.setCreatedAt(point.getCreatedAt());
                    return detail;
                })
                .collect(Collectors.toList());
    }
}
