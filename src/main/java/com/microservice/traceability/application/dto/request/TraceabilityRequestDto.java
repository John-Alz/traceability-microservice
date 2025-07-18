package com.microservice.traceability.application.dto.request;


public record TraceabilityRequestDto(
         Long orderId,
         Long customerId,
         String emailCustomer,
         String previousStatus,
         String newStatus,
         Long employeeId,
         String employeeEmail
) {
}
