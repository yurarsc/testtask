package com.example.testtask.api;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PhoneRequest(@NotNull @Pattern(regexp = "[0-9]{11}") String phone) {
}
