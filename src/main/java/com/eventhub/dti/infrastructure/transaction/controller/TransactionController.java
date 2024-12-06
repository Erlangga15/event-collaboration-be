package com.eventhub.dti.infrastructure.transaction.controller;

import com.eventhub.dti.infrastructure.transaction.dto.CreateTransactionRequestDTO;
import com.eventhub.dti.infrastructure.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vi/transactions")
public class TransactionController {
  private final TransactionService transactionService;

  @Autowired
  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @GetMapping
  public ResponseEntity<List<CreateTransactionRequestDTO>> getAllTransactions() {
    List<CreateTransactionRequestDTO> transactions = transactionService.getAllTransactions();
    return ResponseEntity.ok(transactions);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CreateTransactionRequestDTO> getTransactionById(@PathVariable Long id) {
    Optional<CreateTransactionRequestDTO> transaction = transactionService.getTransactionById(id);
    return transaction.map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @PostMapping
  public ResponseEntity<CreateTransactionRequestDTO> createTransaction(@RequestBody CreateTransactionRequestDTO createTransactionRequestDTO) {
    CreateTransactionRequestDTO createdTransaction = transactionService.createTransaction(createTransactionRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CreateTransactionRequestDTO> updateTransaction(@PathVariable Long id, @RequestBody CreateTransactionRequestDTO createTransactionRequestDTO) {
    Optional<CreateTransactionRequestDTO> updatedTransaction = transactionService.updateTransaction(id, createTransactionRequestDTO);
    return updatedTransaction.map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
    boolean isDeleted = transactionService.deleteTransaction(id);
    return isDeleted ? ResponseEntity.noContent().build()
      : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }
}
