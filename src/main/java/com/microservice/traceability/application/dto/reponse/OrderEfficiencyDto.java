package com.microservice.traceability.application.dto.reponse;

import java.time.LocalDateTime;

public record OrderEfficiencyDto(
         Long orderId,
         LocalDateTime startTime,
         LocalDateTime endTime,
         double processingTimeMinutes,
         String status,
         Long chefId
) {
}
