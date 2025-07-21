package com.microservice.traceability.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeEfficiencyModelTest {

    @Test
    void testNoArgsConstructorAndSettersAndGetters() {
        EmployeeEfficiencyModel model = new EmployeeEfficiencyModel();

        model.setEmployeeId(10L);
        model.setAverageTime(25.5);

        assertEquals(10L, model.getEmployeeId());
        assertEquals(25.5, model.getAverageTime());
    }

    @Test
    void testAllArgsConstructor() {
        EmployeeEfficiencyModel model = new EmployeeEfficiencyModel(20L, 35.0);

        assertEquals(20L, model.getEmployeeId());
        assertEquals(35.0, model.getAverageTime());
    }
}
