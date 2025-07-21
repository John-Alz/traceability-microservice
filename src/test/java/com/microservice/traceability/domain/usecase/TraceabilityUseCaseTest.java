package com.microservice.traceability.domain.usecase;

import com.microservice.traceability.domain.model.EmployeeEfficiencyModel;
import com.microservice.traceability.domain.model.OrderEfficiencyModel;
import com.microservice.traceability.domain.model.PageResult;
import com.microservice.traceability.domain.model.TraceabilityModel;
import com.microservice.traceability.domain.spi.IRestaurantPort;
import com.microservice.traceability.domain.spi.ITraceabilityPersistencePort;
import com.microservice.traceability.domain.spi.IUserSessionPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TraceabilityUseCaseTest {

    @InjectMocks
    private TraceabilityUseCase traceabilityUseCase;

    @Mock
    private ITraceabilityPersistencePort traceabilityPersistencePort;

    @Mock
    private IUserSessionPort userSessionPort;

    @Mock
    private IRestaurantPort restaurantPort;

    @Test
    void createOrderLog_ShouldSetCurrentDateAndCallPersistence() {
        TraceabilityModel traceability = new TraceabilityModel();
        traceability.setOrderId(123L);
        traceability.setRestaurantId(10L);

        traceabilityUseCase.createOrderLog(traceability);

        assertNotNull(traceability.getDate(), "La fecha no debe ser nula");
        verify(traceabilityPersistencePort).createOrderLog(traceability);
    }

    @Test
    void getOrdersLogs_shouldReturnPaginatedTraceabilityLogs() {
        Integer page = 0;
        Integer size = 2;
        Long orderId = 99L;
        Long userId = 10L;

        TraceabilityModel trace1 = new TraceabilityModel();
        TraceabilityModel trace2 = new TraceabilityModel();
        List<TraceabilityModel> traceList = List.of(trace1, trace2);

        PageResult<TraceabilityModel> pageResultMock = new PageResult<>(
                traceList,
                page,
                size,
                1,
                2L
        );

        when(userSessionPort.getUserLoggedId()).thenReturn(userId);
        doNothing().when(traceabilityPersistencePort).validateCustomerPermission(orderId, userId);
        when(traceabilityPersistencePort.getOrdersLogs(page, size, orderId)).thenReturn(pageResultMock);

        PageResult<TraceabilityModel> result = traceabilityUseCase.getOrdersLogs(page, size, orderId);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(0, result.getPage());
        assertEquals(2, result.getSize());
        assertEquals(1, result.getTotalPages());
        assertEquals(2L, result.getTotalElements());

        verify(userSessionPort, times(1)).getUserLoggedId();
        verify(traceabilityPersistencePort, times(1)).validateCustomerPermission(orderId, userId);
        verify(traceabilityPersistencePort, times(1)).getOrdersLogs(page, size, orderId);
    }

    @Test
    void getOrderEfficiency_ShouldReturnCorrectEfficiency() {
        Long userId = 1L;
        Long restaurantId = 100L;
        Long orderId = 200L;
        Long employeeId = 300L;

        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusMinutes(30);

        TraceabilityModel t1 = new TraceabilityModel();
        t1.setOrderId(orderId);
        t1.setDate(start);
        t1.setNewStatus("PENDIENTE");
        t1.setRestaurantId(restaurantId);
        t1.setEmployeeId(employeeId); // importante

        TraceabilityModel t2 = new TraceabilityModel();
        t2.setOrderId(orderId);
        t2.setDate(end);
        t2.setNewStatus("ENTREGADO");
        t2.setRestaurantId(restaurantId);
        t2.setEmployeeId(employeeId);

        when(userSessionPort.getUserLoggedId()).thenReturn(userId);
        when(traceabilityPersistencePort.getAllOrdersLogs()).thenReturn(List.of(t1, t2));

        List<OrderEfficiencyModel> result = traceabilityUseCase.getOrderEfficiency(restaurantId);

        assertEquals(1, result.size());
        OrderEfficiencyModel model = result.get(0);
        assertEquals(orderId, model.getOrderId());
        assertEquals(employeeId, model.getChefId());
        assertEquals("ENTREGADO", model.getStatus());
        assertEquals(30.00, model.getProcessingTimeMinutes());
    }

    @Test
    void getEmployeeEfficiency_ShouldReturnAveragePerEmployee() {
        Long userId = 1L;
        Long restaurantId = 100L;
        Long orderId = 200L;
        Long employeeId = 300L;

        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusMinutes(20);

        TraceabilityModel t1 = new TraceabilityModel();
        t1.setOrderId(orderId);
        t1.setDate(start);
        t1.setNewStatus("PENDIENTE");
        t1.setRestaurantId(restaurantId);
        t1.setEmployeeId(employeeId); // importante

        TraceabilityModel t2 = new TraceabilityModel();
        t2.setOrderId(orderId);
        t2.setDate(end);
        t2.setNewStatus("ENTREGADO");
        t2.setRestaurantId(restaurantId);
        t2.setEmployeeId(employeeId);

        when(userSessionPort.getUserLoggedId()).thenReturn(userId);
        when(traceabilityPersistencePort.getAllOrdersLogs()).thenReturn(List.of(t1, t2));

        var result = traceabilityUseCase.getEmployeeEfficiency(restaurantId);

        assertEquals(1, result.size());
        assertEquals(employeeId, result.get(0).getEmployeeId());
        assertEquals(20.00, result.get(0).getAverageTime());
    }

    @Test
    void getOrderEfficiency_shouldIncludeOrder_WhenStartAndEndExist() {
        // Arrange
        Long restaurantId = 1L;
        Long orderId = 10L;
        Long employeeId = 5L;
        LocalDateTime start = LocalDateTime.now().minusMinutes(20);
        LocalDateTime end = LocalDateTime.now();

        TraceabilityModel startLog = new TraceabilityModel();
        startLog.setOrderId(orderId);
        startLog.setRestaurantId(restaurantId);
        startLog.setDate(start);
        startLog.setNewStatus("PENDIENTE");

        TraceabilityModel endLog = new TraceabilityModel();
        endLog.setOrderId(orderId);
        endLog.setRestaurantId(restaurantId);
        endLog.setDate(end);
        endLog.setNewStatus("ENTREGADO");
        endLog.setEmployeeId(employeeId);

        List<TraceabilityModel> logs = List.of(startLog, endLog);

        when(userSessionPort.getUserLoggedId()).thenReturn(99L);
        doNothing().when(restaurantPort).validateOwnerOfRestaurant(99L, restaurantId);
        when(traceabilityPersistencePort.getAllOrdersLogs()).thenReturn(logs);

        // Act
        List<OrderEfficiencyModel> result = traceabilityUseCase.getOrderEfficiency(restaurantId);

        // Assert
        assertEquals(1, result.size());
        OrderEfficiencyModel efficiency = result.get(0);
        assertEquals(orderId, efficiency.getOrderId());
        assertEquals(employeeId, efficiency.getChefId());
        assertEquals("ENTREGADO", efficiency.getStatus());
        assertEquals(start, efficiency.getStartTime());
        assertEquals(end, efficiency.getEndTime());
    }

    @Test
    void getEmployeeEfficiency_shouldCalculateAverage_WhenValidLogsExist() {
        // Arrange
        Long restaurantId = 1L;
        Long orderId = 100L;
        Long employeeId = 20L;
        LocalDateTime start = LocalDateTime.now().minusMinutes(30);
        LocalDateTime end = LocalDateTime.now();

        TraceabilityModel log1 = new TraceabilityModel();
        log1.setOrderId(orderId);
        log1.setRestaurantId(restaurantId);
        log1.setDate(start);
        log1.setNewStatus("PENDIENTE");

        TraceabilityModel log2 = new TraceabilityModel();
        log2.setOrderId(orderId);
        log2.setRestaurantId(restaurantId);
        log2.setDate(end);
        log2.setNewStatus("ENTREGADO");
        log2.setEmployeeId(employeeId);

        List<TraceabilityModel> logs = List.of(log1, log2);

        when(userSessionPort.getUserLoggedId()).thenReturn(employeeId);
        doNothing().when(restaurantPort).validateOwnerOfRestaurant(employeeId, restaurantId);
        when(traceabilityPersistencePort.getAllOrdersLogs()).thenReturn(logs);

        // Act
        List<EmployeeEfficiencyModel> result = traceabilityUseCase.getEmployeeEfficiency(restaurantId);

        // Assert
        assertEquals(1, result.size());
        EmployeeEfficiencyModel efficiency = result.get(0);
        assertEquals(employeeId, efficiency.getEmployeeId());
        assertTrue(efficiency.getAverageTime() > 0);
    }

    @Test
    void getOrderEfficiency_shouldNotIncludeOrder_WhenStartOrEndIsMissing() {
        // Arrange
        Long restaurantId = 1L;
        Long orderId = 10L;

        // Solo existe el log de estado FINAL (falta el de inicio)
        TraceabilityModel onlyEndLog = new TraceabilityModel();
        onlyEndLog.setOrderId(orderId);
        onlyEndLog.setRestaurantId(restaurantId);
        onlyEndLog.setDate(LocalDateTime.now());
        onlyEndLog.setNewStatus("ENTREGADO");
        onlyEndLog.setEmployeeId(5L);

        List<TraceabilityModel> logs = List.of(onlyEndLog);

        when(userSessionPort.getUserLoggedId()).thenReturn(1L);
        doNothing().when(restaurantPort).validateOwnerOfRestaurant(1L, restaurantId);
        when(traceabilityPersistencePort.getAllOrdersLogs()).thenReturn(logs);

        // Act
        List<OrderEfficiencyModel> result = traceabilityUseCase.getOrderEfficiency(restaurantId);

        // Assert
        assertTrue(result.isEmpty()); // No se debe agregar ningún modelo al resultado
    }

    @Test
    void getEmployeeEfficiency_shouldSkipEmployee_WhenEndTraceIsMissing() {
        // Arrange
        Long restaurantId = 1L;
        Long orderId = 100L;

        // Solo existe log inicial (PENDIENTE), pero no el de fin (ENTREGADO o LISTO)
        TraceabilityModel logInicio = new TraceabilityModel();
        logInicio.setOrderId(orderId);
        logInicio.setRestaurantId(restaurantId);
        logInicio.setDate(LocalDateTime.now());
        logInicio.setNewStatus("PENDIENTE");

        List<TraceabilityModel> logs = List.of(logInicio);

        when(userSessionPort.getUserLoggedId()).thenReturn(1L);
        doNothing().when(restaurantPort).validateOwnerOfRestaurant(1L, restaurantId);
        when(traceabilityPersistencePort.getAllOrdersLogs()).thenReturn(logs);

        // Act
        List<EmployeeEfficiencyModel> result = traceabilityUseCase.getEmployeeEfficiency(restaurantId);

        // Assert
        assertTrue(result.isEmpty()); // No se pudo calcular eficiencia sin fin
    }



}
