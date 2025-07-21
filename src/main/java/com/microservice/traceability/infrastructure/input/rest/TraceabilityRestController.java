package com.microservice.traceability.infrastructure.input.rest;

import com.microservice.traceability.application.dto.reponse.EmployeeEfficiencyDto;
import com.microservice.traceability.application.dto.reponse.OrderEfficiencyDto;
import com.microservice.traceability.application.dto.reponse.SuccessResponseDto;
import com.microservice.traceability.application.dto.reponse.TraceabilityResponseDto;
import com.microservice.traceability.application.dto.request.TraceabilityRequestDto;
import com.microservice.traceability.application.handler.ITraceabilityHandler;
import com.microservice.traceability.domain.model.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders/tracking")
@RequiredArgsConstructor
public class TraceabilityRestController {

    private final ITraceabilityHandler traceabilityHandler;

    @PostMapping()
    public ResponseEntity<SuccessResponseDto> createOrderLog(@RequestBody TraceabilityRequestDto traceabilityRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(traceabilityHandler.createOrderLog(traceabilityRequestDto));
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<PageResult<TraceabilityResponseDto>> getOrdersLogs(Integer page, Integer size, @PathVariable Long orderId) {
        PageResult<TraceabilityResponseDto> traceabilityResponseDtoPageResult = traceabilityHandler.getOrdersLogs(page, size, orderId);
        return ResponseEntity.ok(traceabilityResponseDtoPageResult);
    }

    @GetMapping("/order-efficiency/{restaurantId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<OrderEfficiencyDto>> getOrdersLogsEfficiency(@PathVariable Long restaurantId) {
        List<OrderEfficiencyDto> traceabilityOrdersLogsEfficiency= traceabilityHandler.getAllorderEfficiency(restaurantId);
        return ResponseEntity.ok(traceabilityOrdersLogsEfficiency);
    }

    @GetMapping("/employee-efficiency/{restaurantId}")
//    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<EmployeeEfficiencyDto>> getEmployeeEfficiency(@PathVariable Long restaurantId) {
        List<EmployeeEfficiencyDto> traceabilityEmployeeEfficiency= traceabilityHandler.getEmployeeEfficiency(restaurantId);
        return ResponseEntity.ok(traceabilityEmployeeEfficiency);
    }


}
