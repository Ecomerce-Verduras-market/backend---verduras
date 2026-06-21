package com.verduritas.user.application;

import com.verduritas.user.infrastructure.entity.UserProfileEntity;
import com.verduritas.user.infrastructure.repository.UserProfileRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserProfileRepository repository;

    public UserService(UserProfileRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public UserResponse create(CreateUserRequest request) {
        if (repository.existsByEmail(request.email())) throw new IllegalArgumentException("Email already exists");
        var entity = new UserProfileEntity();
        entity.setFullName(request.fullName());
        entity.setEmail(request.email().toLowerCase());
        entity.setPhone(request.phone());
        return toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public UserResponse findById(UUID id) {
        return repository.findById(id).map(this::toResponse).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private UserResponse toResponse(UserProfileEntity entity) {
        return new UserResponse(entity.getId(), entity.getFullName(), entity.getEmail(), entity.getPhone());
    }
}
