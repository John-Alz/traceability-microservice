package com.microservice.traceability.domain.usecase;

import com.microservice.traceability.domain.api.ITraceabilityServicePort;
import com.microservice.traceability.domain.model.PageResult;
import com.microservice.traceability.domain.model.TraceabilityModel;
import com.microservice.traceability.domain.spi.ITraceabilityPersistencePort;
import com.microservice.traceability.domain.spi.IUserSessionPort;

import java.time.LocalDateTime;

public class TraceabilityUseCase implements ITraceabilityServicePort {

    private final ITraceabilityPersistencePort traceabilityPersistencePort;
    private final IUserSessionPort userSessionPort;

    public TraceabilityUseCase(ITraceabilityPersistencePort traceabilityPersistencePort,IUserSessionPort userSessionPort) {
        this.traceabilityPersistencePort = traceabilityPersistencePort;
        this.userSessionPort = userSessionPort;
    }

    @Override
    public void createOrderLog(TraceabilityModel traceabilityModel) {
        traceabilityModel.setDate(LocalDateTime.now());
        System.out.println("SE GUARDO LA ORDER: " + traceabilityModel.getOrderId());
        System.out.println("SE GUARDO EMAIL CUSTOMER: " + traceabilityModel.getEmailCustomer());
        System.out.println("SE GUARDO STATUS: " + traceabilityModel.getPreviousStatus());
        traceabilityPersistencePort.createOrderLog(traceabilityModel);
    }

    @Override
    public PageResult<TraceabilityModel> getOrdersLogs(Integer page, Integer size, Long orderId) {
        Long userId = userSessionPort.getUserLoggedId();
        traceabilityPersistencePort.validateCustomerPermission(orderId, userId);
        return traceabilityPersistencePort.getOrdersLogs(page, size, orderId);
    }
}
