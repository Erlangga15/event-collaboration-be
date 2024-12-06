package com.eventhub.dti.infrastructure.user.repository;


import com.eventhub.dti.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmailContainsIgnoreCase(String email);
}
