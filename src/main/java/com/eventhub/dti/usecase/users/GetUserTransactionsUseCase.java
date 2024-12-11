package com.eventhub.dti.usecase.users;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eventhub.dti.infrastructure.dto.TransactionResponseDTO;

public interface GetUserTransactionsUseCase {
    Page<TransactionResponseDTO> getUserTransactions(Pageable pageable);

    List<TransactionResponseDTO> getUserTransactions();
}