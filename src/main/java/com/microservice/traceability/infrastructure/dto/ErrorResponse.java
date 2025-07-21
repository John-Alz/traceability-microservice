package com.microservice.traceability.infrastructure.dto;

public record ErrorResponse(
        String message,
        String timeStamp
) {
}
