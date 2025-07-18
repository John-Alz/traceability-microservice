package com.microservice.traceability.infrastructure.input.rest;

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

@RestController
@RequestMapping("api/v1/orders/tracking")
@RequiredArgsConstructor
public class TraceabilityRestController {

    private final ITraceabilityHandler traceabilityHandler;

    @PostMapping()
    public ResponseEntity<SuccessResponseDto> createOrderLog(@RequestBody TraceabilityRequestDto traceabilityRequestDto) {
        System.out.println("VIENE DEL OTRO MICRO: ");
        System.out.println(traceabilityRequestDto.orderId());
        System.out.println("CIERRE DEL OTRO MICRO: ");
        return ResponseEntity.status(HttpStatus.CREATED).body(traceabilityHandler.createOrderLog(traceabilityRequestDto));
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<PageResult<TraceabilityResponseDto>> getOrdersLogs(Integer page, Integer size, @PathVariable Long orderId) {
        PageResult<TraceabilityResponseDto> traceabilityResponseDtoPageResult = traceabilityHandler.getOrdersLogs(page, size, orderId);
        return ResponseEntity.ok(traceabilityResponseDtoPageResult);
    }


}
