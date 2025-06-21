package com.example.testtask.api.data;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferRequest(@NotNull Long userIdTo,
                              @NotNull @DecimalMin(value = "0") BigDecimal amount) {
}
