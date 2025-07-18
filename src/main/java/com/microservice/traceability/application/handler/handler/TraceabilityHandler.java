package com.microservice.traceability.application.handler.handler;

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
}
