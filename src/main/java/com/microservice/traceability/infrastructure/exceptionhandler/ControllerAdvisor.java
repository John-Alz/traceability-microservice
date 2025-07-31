package com.microservice.traceability.infrastructure.exceptionhandler;

import com.microservice.traceability.infrastructure.exception.NoDataFoundException;
import com.microservice.traceability.infrastructure.exception.TraceabilityNotFoundException;
import com.microservice.traceability.infrastructure.exception.UnauthorizedRestaurantAccessException;
import com.microservice.traceability.infrastructure.exception.UnauthorizedTraceabilityAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(TraceabilityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleTraceabilityNotFoundException(TraceabilityNotFoundException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse("No se encontraron registros de trazabilidad para el pedido.", LocalDateTime.now()));
    }

    @ExceptionHandler(UnauthorizedTraceabilityAccessException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorizedTraceabilityAccessException(UnauthorizedTraceabilityAccessException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse("El pedido no pertenece al usuario autenticado.", LocalDateTime.now()));
    }

    @ExceptionHandler(UnauthorizedRestaurantAccessException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorizedRestaurantAccessException(UnauthorizedRestaurantAccessException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNoDataFoundException(NoDataFoundException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage(), LocalDateTime.now()));
    }

}
