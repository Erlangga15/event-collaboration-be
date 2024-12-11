package com.eventhub.dti.usecase.users;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eventhub.dti.infrastructure.dto.UserTicketResponseDTO;

public interface GetUserTicketsUseCase {
    Page<UserTicketResponseDTO> getUserTickets(Pageable pageable);

    List<UserTicketResponseDTO> getUserTickets();
}