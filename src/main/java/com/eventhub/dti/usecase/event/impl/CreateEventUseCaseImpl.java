package com.eventhub.dti.usecase.event.impl;

import com.eventhub.dti.entity.Event;
import com.eventhub.dti.entity.User;
import com.eventhub.dti.infrastructure.category.repository.CategoryRepository;
import com.eventhub.dti.infrastructure.event.dto.CreateEventRequestDTO;
import com.eventhub.dti.infrastructure.event.dto.CreateEventResponseDTO;
import com.eventhub.dti.infrastructure.event.repository.EventRepository;
import com.eventhub.dti.infrastructure.user.repository.UserRepository;
import com.eventhub.dti.usecase.event.CreateEventUseCase;
import org.springframework.stereotype.Service;

import javax.xml.stream.Location;


@Service
public class CreateEventUseCaseImpl implements CreateEventUseCase {
  private final EventRepository eventRepository;
  private final UserRepository userRepository;
  private final CategoryRepository categoryRepository;

  public CreateEventUseCaseImpl(EventRepository eventRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
    this.eventRepository = eventRepository;
    this.userRepository = userRepository;
    this.categoryRepository = categoryRepository;
  }

  @Override
  public CreateEventResponseDTO create(CreateEventRequestDTO req) {
    Event event = req.toEntity();
    User assignedRoles = userRepository.findById(req.getOrganizerId()).orElseThrow(() -> new RuntimeException("Role not found"));
    if (!assignedRoles.getIsOnboardingFinished()) {
      throw new RuntimeException("User is not an role");
    }

    event.setOrganizer(assignedRoles);
    event.setCategory(categoryRepository.findById(req.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found")));
    Event savedEvent = eventRepository.save(event);
    return new CreateEventResponseDTO(
      savedEvent.getId(),
      savedEvent.getOrganizer(),
      savedEvent.getCategory(),
      (Location) savedEvent.getLocation(),
      savedEvent.getName(),
      savedEvent.getDescription(),
      savedEvent.getAvailable(),
      savedEvent.getStartDate(),
      savedEvent.getEndDate(),
      savedEvent.getTotalSeat(),
      savedEvent.getStatus()
    );
  }
}
