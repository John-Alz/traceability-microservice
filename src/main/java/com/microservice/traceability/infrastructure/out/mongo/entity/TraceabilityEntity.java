package com.microservice.traceability.infrastructure.out.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "trazabilidad")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TraceabilityEntity {

    @Id
    private String id;
    private Long orderId;
    private Long customerId;
    private String emailCustomer;
    private LocalDateTime date;
    private String previousStatus;
    private String newStatus;
    private Long employeeId;
    private String employeeEmail;
    private Long restaurantId;

}
