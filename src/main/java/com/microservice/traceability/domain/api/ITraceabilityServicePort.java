package com.microservice.traceability.domain.api;

import com.microservice.traceability.domain.model.EmployeeEfficiencyModel;
import com.microservice.traceability.domain.model.OrderEfficiencyModel;
import com.microservice.traceability.domain.model.PageResult;
import com.microservice.traceability.domain.model.TraceabilityModel;

import java.util.List;

public interface ITraceabilityServicePort {

    void createOrderLog(TraceabilityModel traceabilityModel);
    PageResult<TraceabilityModel> getOrdersLogs(Integer page, Integer size, Long orderId);
    List<OrderEfficiencyModel> getOrderEfficiency(Long restaurantId);
    List<EmployeeEfficiencyModel> getEmployeeEfficiency(Long restaurantId);

}
