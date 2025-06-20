package com.example.testtask.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record EmailRequest(@NotNull @Email String email) {
}
