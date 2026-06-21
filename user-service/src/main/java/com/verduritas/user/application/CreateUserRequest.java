package com.verduritas.user.application;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(@NotBlank String fullName, @Email @NotBlank String email, String phone) {
}
