package com.example.testtask.api;

import java.time.ZonedDateTime;

public record ErrorResponse(ZonedDateTime timestamp,
                            int status,
                            String error,
                            String path) {
}
