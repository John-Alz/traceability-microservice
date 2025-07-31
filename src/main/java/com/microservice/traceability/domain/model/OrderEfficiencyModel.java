package com.microservice.traceability.domain.model;

import java.time.LocalDateTime;

public class OrderEfficiencyModel {

    private Long orderId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double processingTimeMinutes;
    private String status;
    private Long chefId;

    public OrderEfficiencyModel(Long orderId, LocalDateTime startTime, LocalDateTime endTime, double processingTimeMinutes, String status, Long chefId) {
        this.orderId = orderId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.processingTimeMinutes = processingTimeMinutes;
        this.status = status;
        this.chefId = chefId;
    }

    public OrderEfficiencyModel() {

    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public double getProcessingTimeMinutes() {
        return processingTimeMinutes;
    }

    public void setProcessingTimeMinutes(double processingTimeMinutes) {
        this.processingTimeMinutes = processingTimeMinutes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getChefId() {
        return chefId;
    }

    public void setChefId(Long chefId) {
        this.chefId = chefId;
    }
}
