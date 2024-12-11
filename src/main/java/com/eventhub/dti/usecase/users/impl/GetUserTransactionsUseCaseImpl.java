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
import com.eventhub.dti.infrastructure.dto.TransactionResponseDTO;
import com.eventhub.dti.repository.TransactionRepository;
import com.eventhub.dti.repository.UserRepository;
import com.eventhub.dti.usecase.users.GetUserTransactionsUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetUserTransactionsUseCaseImpl implements GetUserTransactionsUseCase {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionResponseDTO> getUserTransactions(Pageable pageable) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        Page<Transaction> transactions = transactionRepository.findByUserAndStatus(user, TransactionStatus.PAID,
                pageable);
        return transactions.map(TransactionResponseDTO::fromTransaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponseDTO> getUserTransactions() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        List<Transaction> transactions = transactionRepository.findByUserAndStatus(user, TransactionStatus.PAID);
        return transactions.stream()
                .map(TransactionResponseDTO::fromTransaction)
                .collect(Collectors.toList());
    }
}