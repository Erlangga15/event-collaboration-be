package com.eventhub.dti.infrastructure.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.eventhub.dti.entity.Review;

import lombok.Data;

@Data
public class EventReviewResponseDTO {
    private UUID id;
    private Integer rating;
    private String comment;
    private UserResponseDTO user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static EventReviewResponseDTO fromReview(Review review) {
        EventReviewResponseDTO response = new EventReviewResponseDTO();
        response.setId(review.getId());
        response.setRating(review.getRating());
        response.setComment(review.getComment());
        response.setUser(UserResponseDTO.fromEntity(review.getUser()));
        response.setCreatedAt(review.getCreatedAt());
        response.setUpdatedAt(review.getUpdatedAt());
        return response;
    }
}
