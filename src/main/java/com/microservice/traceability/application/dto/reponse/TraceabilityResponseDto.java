package com.microservice.traceability.application.dto.reponse;

import java.time.LocalDateTime;

public record TraceabilityResponseDto(
         String id,
         Long orderId,
         Long customerId,
         String emailCustomer,
         LocalDateTime date,
         String previousStatus,
         String newStatus,
         Long employeeId,
         String employeeEmail
) {
}
