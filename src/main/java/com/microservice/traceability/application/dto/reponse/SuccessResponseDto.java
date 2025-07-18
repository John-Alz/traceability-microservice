package com.microservice.traceability.application.dto.reponse;

import java.time.LocalDateTime;

public record SuccessResponseDto(
        String message,
        LocalDateTime date
) {
}
