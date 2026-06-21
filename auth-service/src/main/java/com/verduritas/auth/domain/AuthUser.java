package com.verduritas.auth.domain;

import java.util.Set;
import java.util.UUID;

public record AuthUser(UUID id, String email, String passwordHash, Set<String> roles) {
}
