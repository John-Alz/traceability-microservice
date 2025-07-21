package com.microservice.traceability.application.handler;

import com.microservice.traceability.application.dto.reponse.EmployeeEfficiencyDto;
import com.microservice.traceability.application.dto.reponse.OrderEfficiencyDto;
import com.microservice.traceability.application.dto.reponse.SuccessResponseDto;
import com.microservice.traceability.application.dto.reponse.TraceabilityResponseDto;
import com.microservice.traceability.application.dto.request.TraceabilityRequestDto;
import com.microservice.traceability.domain.model.PageResult;

import java.util.List;

public interface ITraceabilityHandler {

    SuccessResponseDto createOrderLog(TraceabilityRequestDto traceabilityRequestDto);
    PageResult<TraceabilityResponseDto> getOrdersLogs(Integer page, Integer size, Long orderId);
    List<OrderEfficiencyDto> getAllorderEfficiency(Long restaurantId);
    List<EmployeeEfficiencyDto> getEmployeeEfficiency(Long restaurantId);



}
