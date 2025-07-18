package com.microservice.traceability.domain.api;

import com.microservice.traceability.domain.model.PageResult;
import com.microservice.traceability.domain.model.TraceabilityModel;

public interface ITraceabilityServicePort {

    void createOrderLog(TraceabilityModel traceabilityModel);
    PageResult<TraceabilityModel> getOrdersLogs(Integer page, Integer size, Long orderId);


}
