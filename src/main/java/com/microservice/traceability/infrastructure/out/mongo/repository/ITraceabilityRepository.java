package com.microservice.traceability.infrastructure.out.mongo.repository;

import com.microservice.traceability.infrastructure.out.mongo.entity.TraceabilityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ITraceabilityRepository extends MongoRepository<TraceabilityEntity, Long> {

    @Query("{'orderId': ?0 }")
    Page<TraceabilityEntity> getOrderLogsByCustomerId(Long orderId, Pageable paging);

    @Query("{'orderId':  ?0}")
    List<TraceabilityEntity> findByOrderId(Long orderId);


}
