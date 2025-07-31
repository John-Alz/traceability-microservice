package com.microservice.traceability.application.handler.handler;

import com.microservice.traceability.application.dto.reponse.EmployeeEfficiencyDto;
import com.microservice.traceability.application.dto.reponse.OrderEfficiencyDto;
import com.microservice.traceability.application.dto.reponse.SuccessResponseDto;
import com.microservice.traceability.application.dto.reponse.TraceabilityResponseDto;
import com.microservice.traceability.application.dto.request.TraceabilityRequestDto;
import com.microservice.traceability.application.handler.ITraceabilityHandler;
import com.microservice.traceability.application.mapper.TraceabilityMapper;
import com.microservice.traceability.application.utils.ApplicationConstants;
import com.microservice.traceability.domain.api.ITraceabilityServicePort;
import com.microservice.traceability.domain.model.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TraceabilityHandler implements ITraceabilityHandler {

    private final ITraceabilityServicePort traceabilityServicePort;
    private final TraceabilityMapper traceabilityMapper;


    @Override
    public SuccessResponseDto createOrderLog(TraceabilityRequestDto traceabilityRequestDto) {
        traceabilityServicePort.createOrderLog(traceabilityMapper.requestToModel(traceabilityRequestDto));
        return new SuccessResponseDto(ApplicationConstants.CREATE_LOG_MESSAGE, LocalDateTime.now());
    }

    @Override
    public PageResult<TraceabilityResponseDto> getOrdersLogs(Integer page, Integer size, Long orderId) {
        return traceabilityMapper.modelListToResponseList(traceabilityServicePort.getOrdersLogs(page, size, orderId));
    }

    @Override
    public List<OrderEfficiencyDto> getAllorderEfficiency(Long restaurantId) {
        return traceabilityMapper.modelListToResponseList(traceabilityServicePort.getOrderEfficiency(restaurantId));
    }

    @Override
    public List<EmployeeEfficiencyDto> getEmployeeEfficiency(Long restaurantId) {
        return traceabilityMapper.modelEmployeeListToResponseList(traceabilityServicePort.getEmployeeEfficiency(restaurantId));
    }
}
