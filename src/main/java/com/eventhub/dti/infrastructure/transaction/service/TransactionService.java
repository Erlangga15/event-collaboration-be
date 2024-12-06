package com.eventhub.dti.infrastructure.transaction.service;

import com.eventhub.dti.entity.*;
import com.eventhub.dti.infrastructure.transaction.dto.CreateTransactionRequestDTO;
import com.eventhub.dti.infrastructure.transaction.dto.CreateTransactionResponseDTO;
import com.eventhub.dti.infrastructure.transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
  private final TransactionRepository transactionRepository;
  private final CreateTransactionResponseDTO createTransactionResponseDTO;

  @Autowired
  public TransactionService(TransactionRepository transactionRepository, CreateTransactionResponseDTO createTransactionResponseDTO) {
    this.transactionRepository = transactionRepository;
    this.createTransactionResponseDTO = createTransactionResponseDTO;
  }

  public List<CreateTransactionRequestDTO> getAllTransactions() {
    List<Transaction> transactions = transactionRepository.findAll();
    return transactions.stream()
      .map(createTransactionResponseDTO::toDTO)
      .toList();
  }

  public Optional<CreateTransactionRequestDTO> getTransactionById(Long id) {
    Optional<Transaction> transaction = transactionRepository.findById(id);
    return transaction.map(createTransactionResponseDTO::toDTO);
  }

  public CreateTransactionRequestDTO createTransaction(CreateTransactionRequestDTO createTransactionRequestDTO) {
    Transaction transaction = createTransactionResponseDTO.toEntity(createTransactionRequestDTO);
    transaction = transactionRepository.save(transaction);
    return createTransactionResponseDTO.toDTO(transaction);
  }

  public Optional<CreateTransactionRequestDTO> updateTransaction(Long id, CreateTransactionRequestDTO createTransactionRequestDTO) {
    if (transactionRepository.existsById(id)) {
      createTransactionRequestDTO.setId(id);
      Transaction transaction = createTransactionResponseDTO.toEntity(createTransactionRequestDTO);
      transaction = transactionRepository.save(transaction);
      return Optional.of(createTransactionResponseDTO.toDTO(transaction));
    }
    return Optional.empty();
  }

  public boolean deleteTransaction(Long id) {
    if (transactionRepository.existsById(id)) {
      transactionRepository.deleteById(id);
      return true;
    }
    return false;
  }
}
