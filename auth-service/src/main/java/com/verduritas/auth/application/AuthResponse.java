package com.verduritas.auth.application;

public record AuthResponse(String userId, String email, String accessToken) {
}
