package com.verduritas.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.verduritas.auth.infrastructure.entity.AuthUserEntity;
import com.verduritas.auth.infrastructure.repository.AuthUserRepository;
import com.verduritas.auth.infrastructure.security.JwtService;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

class AuthServiceTest {
    private final AuthUserRepository repository = mock(AuthUserRepository.class);
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private final JwtService jwtService = mock(JwtService.class);
    private final AuthService authService = new AuthService(repository, passwordEncoder, jwtService);

    @Test
    void registersANewUser() {
        when(repository.existsByEmail("ana@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("hash");
        when(repository.save(any(AuthUserEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(jwtService.generate(any(AuthUserEntity.class))).thenReturn("token");

        var response = authService.register(new RegisterRequest("ana@example.com", "password123"));

        assertThat(response.email()).isEqualTo("ana@example.com");
        assertThat(response.accessToken()).isEqualTo("token");
    }

    @Test
    void rejectsInvalidCredentials() {
        var user = new AuthUserEntity();
        user.setEmail("ana@example.com");
        user.setPasswordHash("hash");
        user.setRoles(Set.of("ROLE_CUSTOMER"));
        when(repository.findByEmail("ana@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong-password", "hash")).thenReturn(false);

        assertThatThrownBy(() -> authService.login(new LoginRequest("ana@example.com", "wrong-password")))
                .isInstanceOf(BadCredentialsException.class);
    }
}
