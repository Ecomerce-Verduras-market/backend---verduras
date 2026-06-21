package com.verduritas.auth.application;

import com.verduritas.auth.infrastructure.entity.AuthUserEntity;
import com.verduritas.auth.infrastructure.repository.AuthUserRepository;
import com.verduritas.auth.infrastructure.security.JwtService;
import java.util.Set;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final AuthUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(AuthUserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (repository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email is already registered");
        }
        var user = new AuthUserEntity();
        user.setEmail(request.email().toLowerCase());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setRoles(Set.of("ROLE_CUSTOMER"));
        repository.save(user);
        return new AuthResponse(user.getId().toString(), user.getEmail(), jwtService.generate(user));
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        var user = repository.findByEmail(request.email().toLowerCase())
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid credentials");
        }
        return new AuthResponse(user.getId().toString(), user.getEmail(), jwtService.generate(user));
    }
}
