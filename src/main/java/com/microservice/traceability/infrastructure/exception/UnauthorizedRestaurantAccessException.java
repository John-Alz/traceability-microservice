package com.microservice.traceability.infrastructure.exception;

public class UnauthorizedRestaurantAccessException extends RuntimeException {
    public UnauthorizedRestaurantAccessException(String message){
        super(message);
    }
}
