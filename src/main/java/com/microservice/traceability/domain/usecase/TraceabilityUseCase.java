package com.microservice.traceability.domain.usecase;

import com.microservice.traceability.domain.api.ITraceabilityServicePort;
import com.microservice.traceability.domain.model.EmployeeEfficiencyModel;
import com.microservice.traceability.domain.model.OrderEfficiencyModel;
import com.microservice.traceability.domain.model.PageResult;
import com.microservice.traceability.domain.model.TraceabilityModel;
import com.microservice.traceability.domain.spi.IRestaurantPort;
import com.microservice.traceability.domain.spi.ITraceabilityPersistencePort;
import com.microservice.traceability.domain.spi.IUserSessionPort;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TraceabilityUseCase implements ITraceabilityServicePort {

    private final ITraceabilityPersistencePort traceabilityPersistencePort;
    private final IUserSessionPort userSessionPort;
    private final IRestaurantPort restaurantPort;

    public TraceabilityUseCase(ITraceabilityPersistencePort traceabilityPersistencePort,IUserSessionPort userSessionPort, IRestaurantPort restaurantPort) {
        this.traceabilityPersistencePort = traceabilityPersistencePort;
        this.userSessionPort = userSessionPort;
        this.restaurantPort = restaurantPort;
    }

    @Override
    public void createOrderLog(TraceabilityModel traceabilityModel) {
        traceabilityModel.setDate(LocalDateTime.now());
        traceabilityPersistencePort.createOrderLog(traceabilityModel);
    }

    @Override
    public PageResult<TraceabilityModel> getOrdersLogs(Integer page, Integer size, Long orderId) {
        Long userId = userSessionPort.getUserLoggedId();
        traceabilityPersistencePort.validateCustomerPermission(orderId, userId);
        return traceabilityPersistencePort.getOrdersLogs(page, size, orderId);
    }

    @Override
    public List<OrderEfficiencyModel> getOrderEfficiency(Long restaurantId) {
        Long userId = userSessionPort.getUserLoggedId();
        restaurantPort.validateOwnerOfRestaurant(userId, restaurantId);

        List<TraceabilityModel> allOrdersLogs = traceabilityPersistencePort.getAllOrdersLogs().stream()
                .filter(log -> restaurantId.equals(log.getRestaurantId()))
                .toList();
        Map<Long, List<TraceabilityModel>> tracesByOrder = allOrdersLogs.stream()
                .collect(Collectors.groupingBy(TraceabilityModel::getOrderId));
        List<OrderEfficiencyModel> result = new ArrayList<>();
        for (Map.Entry<Long, List<TraceabilityModel>> entry : tracesByOrder.entrySet()) {
            Long orderId = entry.getKey();
            List<TraceabilityModel> traces = entry.getValue();

            traces.sort(Comparator.comparing(TraceabilityModel::getDate));

            LocalDateTime start = traces.stream()
                    .filter(t -> "PENDIENTE".equals(t.getNewStatus()))
                    .map(TraceabilityModel::getDate)
                    .findFirst()
                    .orElse(null);

            LocalDateTime end = traces.stream()
                    .filter(t -> "ENTREGADO".equals(t.getNewStatus()))
                    .map(TraceabilityModel::getDate)
                    .findFirst()
                    .orElse(null);

            if (start != null && end != null) {
                double minutes = Duration.between(start, end).toMillis() / 60000.0;
                double minutesRounded = BigDecimal.valueOf(minutes)
                        .setScale(2, RoundingMode.HALF_UP)
                        .doubleValue();

                OrderEfficiencyModel model = new OrderEfficiencyModel();
                model.setOrderId(orderId);
                model.setStartTime(start);
                model.setEndTime(end);
                model.setProcessingTimeMinutes(minutesRounded);
                model.setStatus(traces.get(traces.size() - 1).getNewStatus());
                model.setChefId(traces.get(traces.size() -1).getEmployeeId());
                result.add(model);
            }
        }
        return result;
    }

    @Override
    public List<EmployeeEfficiencyModel> getEmployeeEfficiency(Long restaurantId) {
        Long chefId = userSessionPort.getUserLoggedId();
        restaurantPort.validateOwnerOfRestaurant(chefId, restaurantId);

        List<TraceabilityModel> allOrdersLogs = traceabilityPersistencePort.getAllOrdersLogs().stream()
                .filter(log -> restaurantId.equals(log.getRestaurantId()))// ✅ filtro agregado
                .toList();

        // 1. Agrupar los logs por ID de orden
        Map<Long, List<TraceabilityModel>> tracesByOrder = allOrdersLogs.stream()
                .collect(Collectors.groupingBy(TraceabilityModel::getOrderId));

        // 2. Map donde acumulamos los tiempos por empleado
        Map<Long, List<Double>> tiemposPorEmpleado = new HashMap<>();

        // 3. Recorrer cada orden para calcular tiempo de preparación por empleado
        for (List<TraceabilityModel> traces : tracesByOrder.values()) {
            Optional<LocalDateTime> start = traces.stream()
                    .filter(t -> "PENDIENTE".equals(t.getNewStatus()))
                    .map(TraceabilityModel::getDate)
                    .findFirst();

            Optional<TraceabilityModel> endTrace = traces.stream()
                    .filter(t -> "ENTREGADO".equals(t.getNewStatus()))
                    .findFirst();

            if (start.isPresent() && endTrace.isPresent()) {
                double minutos = Duration.between(start.get(), endTrace.get().getDate()).toMillis() / 60000.0;
                double minutosRedondeados = BigDecimal.valueOf(minutos)
                        .setScale(2, RoundingMode.HALF_UP)
                        .doubleValue();
                Long empleadoId = endTrace.get().getEmployeeId();

                // Agregar el tiempo al empleado correspondiente
                tiemposPorEmpleado.computeIfAbsent(empleadoId, k -> new ArrayList<>()).add(minutosRedondeados);
            }
        }

        // 4. Calcular promedio por empleado
        Map<Long, Double> promedioPorEmpleado = new HashMap<>();
        for (Map.Entry<Long, List<Double>> entry : tiemposPorEmpleado.entrySet()) {
            double promedio = entry.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            promedioPorEmpleado.put(entry.getKey(), promedio);
        }

        // 5. Mapear a modelo de respuesta
        List<EmployeeEfficiencyModel> result = new ArrayList<>();
        for (Map.Entry<Long, Double> entry : promedioPorEmpleado.entrySet()) {
            EmployeeEfficiencyModel model = new EmployeeEfficiencyModel();
            model.setEmployeeId(entry.getKey());
            model.setAverageTime(entry.getValue());
            result.add(model);
        }

        return result;
    }


}
