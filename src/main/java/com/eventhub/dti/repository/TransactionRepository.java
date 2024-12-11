package com.eventhub.dti.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventhub.dti.entity.Event;
import com.eventhub.dti.entity.Transaction;
import com.eventhub.dti.entity.User;
import com.eventhub.dti.entity.enums.TransactionStatus;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @EntityGraph(attributePaths = { "user", "event", "ticket" })
    Page<Transaction> findByEventAndStatus(Event event, TransactionStatus status, Pageable pageable);

    @EntityGraph(attributePaths = { "user", "event", "ticket" })
    List<Transaction> findByEventAndStatus(Event event, TransactionStatus status);

    @EntityGraph(attributePaths = { "user", "event", "ticket" })
    Page<Transaction> findByUserAndStatus(User user, TransactionStatus status, Pageable pageable);

    @EntityGraph(attributePaths = { "user", "event", "ticket" })
    List<Transaction> findByUserAndStatus(User user, TransactionStatus status);
}