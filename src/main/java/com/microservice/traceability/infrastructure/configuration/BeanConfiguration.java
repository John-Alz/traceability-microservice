package com.microservice.traceability.infrastructure.configuration;

import com.microservice.traceability.domain.api.ITraceabilityServicePort;
import com.microservice.traceability.domain.spi.ITraceabilityPersistencePort;
import com.microservice.traceability.domain.spi.IUserSessionPort;
import com.microservice.traceability.domain.usecase.TraceabilityUseCase;
import com.microservice.traceability.infrastructure.out.mongo.adapter.TraceabilityMongoAdapter;
import com.microservice.traceability.infrastructure.out.mongo.adapter.UserSessionAdapter;
import com.microservice.traceability.infrastructure.out.mongo.mapper.ITraceabilityEntityMapper;
import com.microservice.traceability.infrastructure.out.mongo.repository.ITraceabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ITraceabilityRepository traceabilityRepository;
    private final ITraceabilityEntityMapper traceabilityEntityMapper;

    @Bean
    public ITraceabilityPersistencePort traceabilityPersistencePort() {
        return new TraceabilityMongoAdapter(traceabilityRepository, traceabilityEntityMapper);
    }

    @Bean
    public ITraceabilityServicePort traceabilityServicePort() {
        return new TraceabilityUseCase(traceabilityPersistencePort(), userSessionPort());
    }

    @Bean
    public IUserSessionPort userSessionPort() {
        return new UserSessionAdapter();
    }
}
