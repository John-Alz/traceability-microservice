package com.microservice.traceability.domain.spi;

import com.microservice.traceability.domain.model.PageResult;
import com.microservice.traceability.domain.model.TraceabilityModel;

public interface ITraceabilityPersistencePort {

    void createOrderLog(TraceabilityModel traceabilityModel);
    PageResult<TraceabilityModel> getOrdersLogs(Integer page, Integer size, Long orderId);
    void validateCustomerPermission(Long orderId, Long userId);
}
