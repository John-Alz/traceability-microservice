package com.microservice.traceability.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderEfficiencyModelTest {

    @Test
    void testNoArgsConstructorAndSettersAndGetters() {
        OrderEfficiencyModel model = new OrderEfficiencyModel();

        LocalDateTime start = LocalDateTime.of(2025, 7, 18, 12, 0);
        LocalDateTime end = LocalDateTime.of(2025, 7, 18, 12, 30);

        model.setOrderId(1L);
        model.setStartTime(start);
        model.setEndTime(end);
        model.setProcessingTimeMinutes(30.0);
        model.setStatus("COMPLETED");
        model.setChefId(100L);

        assertEquals(1L, model.getOrderId());
        assertEquals(start, model.getStartTime());
        assertEquals(end, model.getEndTime());
        assertEquals(30.0, model.getProcessingTimeMinutes());
        assertEquals("COMPLETED", model.getStatus());
        assertEquals(100L, model.getChefId());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime start = LocalDateTime.of(2025, 7, 18, 14, 0);
        LocalDateTime end = LocalDateTime.of(2025, 7, 18, 14, 45);

        OrderEfficiencyModel model = new OrderEfficiencyModel(
                2L,
                start,
                end,
                45.0,
                "READY",
                200L
        );

        assertEquals(2L, model.getOrderId());
        assertEquals(start, model.getStartTime());
        assertEquals(end, model.getEndTime());
        assertEquals(45.0, model.getProcessingTimeMinutes());
        assertEquals("READY", model.getStatus());
        assertEquals(200L, model.getChefId());
    }
}
