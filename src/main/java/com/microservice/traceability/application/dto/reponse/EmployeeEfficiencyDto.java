package com.microservice.traceability.application.dto.reponse;

public record EmployeeEfficiencyDto(
         Long employeeId,
         double averageTime
) {
}
