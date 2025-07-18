package com.microservice.traceability.infrastructure.out.mongo.mapper;

import com.microservice.traceability.domain.model.TraceabilityModel;
import com.microservice.traceability.infrastructure.out.mongo.entity.TraceabilityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ITraceabilityEntityMapper {

    TraceabilityEntity modelToEntity(TraceabilityModel traceabilityModel);

    List<TraceabilityModel> entityListToModelList(List<TraceabilityEntity> traceabilityEntities);

}
