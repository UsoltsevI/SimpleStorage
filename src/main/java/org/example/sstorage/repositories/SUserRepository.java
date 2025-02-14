package org.example.sstorage.repositories;

import org.example.sstorage.entities.SUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SUserRepository extends JpaRepository<SUser, UUID> {
    Optional<SUser> findByUsername(String username);
}
