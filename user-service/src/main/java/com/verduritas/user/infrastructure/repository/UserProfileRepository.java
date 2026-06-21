package com.verduritas.user.infrastructure.repository;

import com.verduritas.user.infrastructure.entity.UserProfileEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfileEntity, UUID> {
    boolean existsByEmail(String email);
}
