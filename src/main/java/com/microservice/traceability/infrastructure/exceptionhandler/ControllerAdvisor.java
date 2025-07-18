package com.microservice.traceability.infrastructure.exceptionhandler;

import com.microservice.traceability.infrastructure.exception.TraceabilityNotFoundException;
import com.microservice.traceability.infrastructure.exception.UnauthorizedTraceabilityAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(TraceabilityNotFoundException.class)
    public ResponseEntity<ExceptionRespnse> handleTraceabilityNotFoundException(TraceabilityNotFoundException e) {
        return ResponseEntity.badRequest().body(new ExceptionRespnse("No se encontraron registros de trazabilidad para el pedido.", LocalDateTime.now()));
    }

    @ExceptionHandler(UnauthorizedTraceabilityAccessException.class)
    public ResponseEntity<ExceptionRespnse> handleUnauthorizedTraceabilityAccessException(UnauthorizedTraceabilityAccessException e) {
        return ResponseEntity.badRequest().body(new ExceptionRespnse("El pedido no pertenece al usuario autenticado.", LocalDateTime.now()));
    }
}
