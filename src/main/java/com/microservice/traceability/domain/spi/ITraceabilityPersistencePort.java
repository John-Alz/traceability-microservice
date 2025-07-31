package com.microservice.traceability.domain.spi;

import com.microservice.traceability.domain.model.PageResult;
import com.microservice.traceability.domain.model.TraceabilityModel;

import java.util.List;

public interface ITraceabilityPersistencePort {

    void createOrderLog(TraceabilityModel traceabilityModel);
    PageResult<TraceabilityModel> getOrdersLogs(Integer page, Integer size, Long orderId);
    void validateCustomerPermission(Long orderId, Long userId);
    List<TraceabilityModel> getAllOrdersLogs();
}
