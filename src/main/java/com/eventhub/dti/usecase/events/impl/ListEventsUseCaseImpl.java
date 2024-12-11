package com.eventhub.dti.usecase.events.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventhub.dti.entity.Event;
import com.eventhub.dti.entity.Ticket;
import com.eventhub.dti.infrastructure.dto.EventFilterRequestDTO;
import com.eventhub.dti.infrastructure.dto.EventResponseDTO;
import com.eventhub.dti.repository.EventRepository;
import com.eventhub.dti.usecase.events.ListEventsUseCase;

import jakarta.persistence.criteria.Join;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@Service
@RequiredArgsConstructor
public class ListEventsUseCaseImpl implements ListEventsUseCase {

    private final EventRepository eventRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<EventResponseDTO> listEvents(EventFilterRequestDTO filter, Pageable pageable) {
        Specification<Event> spec = createSpecification(filter);
        Page<Event> events = eventRepository.findAll(spec, pageable);
        return events.map(EventResponseDTO::fromEvent);
    }

    private Specification<Event> createSpecification(EventFilterRequestDTO filter) {
        Specification<Event> spec = Specification.where(null);

        if (filter.getSearchTerm() != null && !filter.getSearchTerm().trim().isEmpty()) {
            spec = spec.and((root, query, cb) -> {
                String searchTermLike = "%" + filter.getSearchTerm().toLowerCase() + "%";
                return cb.or(
                        cb.like(cb.lower(root.get("name")), searchTermLike),
                        cb.like(cb.lower(root.get("description")), searchTermLike));
            });
        }

        if (filter.getCategory() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("category"), filter.getCategory()));
        }

        if (filter.getStartDate() != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("startDate"), filter.getStartDate()));
        }

        if (filter.getEndDate() != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("endDate"), filter.getEndDate()));
        }

        if (filter.getVenueName() != null) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("venueName")),
                    "%" + filter.getVenueName().toLowerCase() + "%"));
        }

        if (filter.getTicketType() != null) {
            spec = spec.and((root, query, cb) -> {
                Join<Event, Ticket> ticketJoin = root.join("tickets");
                return cb.equal(ticketJoin.get("type"), filter.getTicketType());
            });
        }

        if (filter.getLocation() != null) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("venueAddress")),
                    "%" + filter.getLocation().toLowerCase() + "%"));
        }

        return spec;
    }
}
