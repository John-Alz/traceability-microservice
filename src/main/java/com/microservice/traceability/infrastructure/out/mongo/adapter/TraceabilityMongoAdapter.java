package com.microservice.traceability.infrastructure.out.mongo.adapter;

import com.microservice.traceability.domain.model.PageResult;
import com.microservice.traceability.domain.model.TraceabilityModel;
import com.microservice.traceability.domain.spi.ITraceabilityPersistencePort;
import com.microservice.traceability.infrastructure.exception.TraceabilityNotFoundException;
import com.microservice.traceability.infrastructure.exception.UnauthorizedTraceabilityAccessException;
import com.microservice.traceability.infrastructure.out.mongo.entity.TraceabilityEntity;
import com.microservice.traceability.infrastructure.out.mongo.mapper.ITraceabilityEntityMapper;
import com.microservice.traceability.infrastructure.out.mongo.repository.ITraceabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class TraceabilityMongoAdapter implements ITraceabilityPersistencePort {

    private final ITraceabilityRepository traceabilityRepository;
    private final ITraceabilityEntityMapper traceabilityEntityMapper;

    @Override
    public void createOrderLog(TraceabilityModel traceabilityModel) {
        TraceabilityEntity traceability = traceabilityEntityMapper.modelToEntity(traceabilityModel);
        System.out.println("SE GUARDA EN LA DB: ");
        System.out.println(traceability.getOrderId());
        System.out.println(traceability.getEmailCustomer());
        System.out.println(traceability.getNewStatus());
        System.out.println("FINNNN");
        traceabilityRepository.save(traceability);
    }

    @Override
    public PageResult<TraceabilityModel> getOrdersLogs(Integer page, Integer size, Long orderId) {
        System.out.println(orderId);
        Pageable paging = PageRequest.of(page, size);
        Page<TraceabilityEntity> traceabilityPage = traceabilityRepository.getOrderLogsByCustomerId(orderId, paging);
        List<TraceabilityModel> traceabilityList = traceabilityEntityMapper.entityListToModelList(traceabilityPage.getContent());
        return new PageResult<>(
                traceabilityList,
                traceabilityPage.getNumber(),
                traceabilityPage.getSize(),
                traceabilityPage.getTotalPages(),
                traceabilityPage.getTotalElements()
        );
    }

    @Override
    public void validateCustomerPermission(Long orderId, Long userId) {
        System.out.println(userId);
        List<TraceabilityEntity> traceabilityFoundList = traceabilityRepository.findByOrderId(orderId);
        if (traceabilityFoundList.isEmpty()) {
            throw new TraceabilityNotFoundException();
        }
        for (TraceabilityEntity traceability : traceabilityFoundList) {
            if (!traceability.getCustomerId().equals(userId)) {
                throw new UnauthorizedTraceabilityAccessException();
            }
        }
    }

    @Override
    public List<TraceabilityModel> getAllOrdersLogs() {
        return traceabilityEntityMapper.entityListToModelList(traceabilityRepository.findAll());
    }


}
