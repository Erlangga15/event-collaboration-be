package com.eventhub.dti.infrastructure.user.repository;

import com.eventhub.dti.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(String user);

  @Query("SELECT r FROM Role r WHERE r.name = ?1")
  Role findByNameQuery(String name);
}
