package com.eventhub.dti.infrastructure.transaction.repository;

import com.eventhub.dti.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
