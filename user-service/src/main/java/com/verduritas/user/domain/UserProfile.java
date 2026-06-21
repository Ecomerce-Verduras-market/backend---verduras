package com.verduritas.user.domain;

import java.util.UUID;

public record UserProfile(UUID id, String fullName, String email, String phone) {
}
