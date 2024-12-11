package com.eventhub.dti.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventhub.dti.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findByReferralCode(String referralCode);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
