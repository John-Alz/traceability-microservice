package com.microservice.traceability.infrastructure.exceptionhandler;

import java.time.LocalDateTime;

public record ExceptionRespnse(String message, LocalDateTime time) {
}
