package com.eventhub.dti.infrastructure.review.service;

import com.eventhub.dti.entity.Event;
import com.eventhub.dti.entity.User;
import com.eventhub.dti.infrastructure.event.repository.EventRepository;
import com.eventhub.dti.infrastructure.review.dto.CreateReviewResponseDTO;
import com.eventhub.dti.infrastructure.review.repository.ReviewRepository;
import com.eventhub.dti.infrastructure.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final UserRepository userRepository;
  private final EventRepository eventRepository;

  public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, EventRepository eventRepository) {
    this.reviewRepository = reviewRepository;
    this.userRepository = userRepository;
    this.eventRepository = eventRepository;
  }

  public Page<CreateReviewResponseDTO> getReviewsByEventId(Long eventId, Pageable pageable) {
    return reviewRepository.findByEventId(eventId, pageable)
      .map(review -> {
        var responseDTO = new CreateReviewResponseDTO();
        responseDTO.setId(review.getId());
        responseDTO.setUserId(review.getUser().getId());
        responseDTO.setEventId(review.getEvent().getId());
        responseDTO.setRating(responseDTO.getRating());
        responseDTO.setDescription(review.getDescription());
        responseDTO.setImageUrl(review.getImageUrl());
        responseDTO.setStatus(review.getStatus());

        Event event = eventRepository.findById(review.getEvent().getId()).orElse(null);
        if (event != null) {
          responseDTO.getEventTitle(event.getTitle());
          responseDTO.setEventDateTimeStart(event.getStartDate());
          responseDTO.setEventDateTimeEnd(event.getEndDate());
        }

        User user = userRepository.findById(review.getUser().getId()).orElse(null);
        if (user != null) {
          responseDTO.setUserName(user.getName());
        }

        return responseDTO;
      });
  }

}
