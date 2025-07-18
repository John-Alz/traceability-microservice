package com.microservice.traceability.application.mapper;

import com.microservice.traceability.application.dto.reponse.TraceabilityResponseDto;
import com.microservice.traceability.application.dto.request.TraceabilityRequestDto;
import com.microservice.traceability.domain.model.PageResult;
import com.microservice.traceability.domain.model.TraceabilityModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface TraceabilityMapper {

    TraceabilityModel requestToModel(TraceabilityRequestDto traceabilityRequestDto);

    PageResult<TraceabilityResponseDto> modelListToResponseList(PageResult<TraceabilityModel> traceabilityModels);

}
