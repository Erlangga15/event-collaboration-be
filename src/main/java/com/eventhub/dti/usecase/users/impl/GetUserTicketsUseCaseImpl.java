package com.eventhub.dti.usecase.users.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventhub.dti.common.exceptions.DataNotFoundException;
import com.eventhub.dti.entity.Transaction;
import com.eventhub.dti.entity.User;
import com.eventhub.dti.entity.enums.TransactionStatus;
import com.eventhub.dti.infrastructure.dto.UserTicketResponseDTO;
import com.eventhub.dti.repository.TransactionRepository;
import com.eventhub.dti.repository.UserRepository;
import com.eventhub.dti.usecase.users.GetUserTicketsUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetUserTicketsUseCaseImpl implements GetUserTicketsUseCase {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<UserTicketResponseDTO> getUserTickets(Pageable pageable) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        Page<Transaction> transactions = transactionRepository.findByUserAndStatus(user, TransactionStatus.PAID,
                pageable);
        return transactions.map(UserTicketResponseDTO::fromTransaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserTicketResponseDTO> getUserTickets() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        List<Transaction> transactions = transactionRepository.findByUserAndStatus(user, TransactionStatus.PAID);
        return transactions.stream()
                .map(UserTicketResponseDTO::fromTransaction)
                .collect(Collectors.toList());
    }
}