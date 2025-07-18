package com.microservice.traceability.domain.model;

import java.time.LocalDateTime;

public class TraceabilityModel {

    private String id;
    private Long orderId;
    private Long customerId;
    private String emailCustomer;
    private LocalDateTime date;
    private String previousStatus;
    private String newStatus;
    private Long employeeId;
    private String employeeEmail;

    public TraceabilityModel(String id, Long orderId, Long customerId, String emailCustomer, LocalDateTime date, String previousStatus, String newStatus, Long employeeId, String employeeEmail) {
        this.id = id;
        this.orderId = orderId;
        this.customerId = customerId;
        this.emailCustomer = emailCustomer;
        this.date = date;
        this.previousStatus = previousStatus;
        this.newStatus = newStatus;
        this.employeeId = employeeId;
        this.employeeEmail = employeeEmail;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getEmailCustomer() {
        return emailCustomer;
    }

    public void setEmailCustomer(String emailCustomer) {
        this.emailCustomer = emailCustomer;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(String previousStatus) {
        this.previousStatus = previousStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }
}
