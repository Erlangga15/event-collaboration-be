package com.eventhub.dti.infrastructure.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class UserPointsResponseDTO {
    private UUID userId;
    private int totalPoints;
    private List<PointDetailDTO> pointDetails;

    @Data
    public static class PointDetailDTO {
        private UUID pointId;
        private int points;
        private LocalDateTime expiryDate;
        private LocalDateTime createdAt;
    }
}
