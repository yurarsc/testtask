package com.example.testtask.api.data;

import java.time.ZonedDateTime;

public record ErrorResponse(ZonedDateTime timestamp,
                            int status,
                            String error,
                            String path) {
}
