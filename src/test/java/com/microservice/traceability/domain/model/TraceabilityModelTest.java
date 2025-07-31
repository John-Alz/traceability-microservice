package com.microservice.traceability.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TraceabilityModelTest {

    @Test
    void testNoArgsConstructorAndSettersAndGetters() {
        TraceabilityModel model = new TraceabilityModel();
        LocalDateTime now = LocalDateTime.now();

        model.setId("abc123");
        model.setOrderId(1L);
        model.setCustomerId(2L);
        model.setEmailCustomer("customer@example.com");
        model.setDate(now);
        model.setPreviousStatus("PENDIENTE");
        model.setNewStatus("ENTREGADO");
        model.setEmployeeId(3L);
        model.setEmployeeEmail("employee@example.com");
        model.setRestaurantId(4L);

        assertEquals("abc123", model.getId());
        assertEquals(1L, model.getOrderId());
        assertEquals(2L, model.getCustomerId());
        assertEquals("customer@example.com", model.getEmailCustomer());
        assertEquals(now, model.getDate());
        assertEquals("PENDIENTE", model.getPreviousStatus());
        assertEquals("ENTREGADO", model.getNewStatus());
        assertEquals(3L, model.getEmployeeId());
        assertEquals("employee@example.com", model.getEmployeeEmail());
        assertEquals(4L, model.getRestaurantId());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime date = LocalDateTime.now();
        TraceabilityModel model = new TraceabilityModel(
                "id123",
                10L,
                20L,
                "client@mail.com",
                date,
                "PREPARACION",
                "LISTO",
                30L,
                "chef@mail.com",
                40L
        );

        assertEquals("id123", model.getId());
        assertEquals(10L, model.getOrderId());
        assertEquals(20L, model.getCustomerId());
        assertEquals("client@mail.com", model.getEmailCustomer());
        assertEquals(date, model.getDate());
        assertEquals("PREPARACION", model.getPreviousStatus());
        assertEquals("LISTO", model.getNewStatus());
        assertEquals(30L, model.getEmployeeId());
        assertEquals("chef@mail.com", model.getEmployeeEmail());
        assertEquals(40L, model.getRestaurantId());
    }
}
