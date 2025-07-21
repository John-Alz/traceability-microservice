package com.microservice.traceability.domain.model;

public class EmployeeEfficiencyModel {

    private Long employeeId;
    private double averageTime;

    public EmployeeEfficiencyModel(Long employeeId, double averageTime) {
        this.employeeId = employeeId;
        this.averageTime = averageTime;
    }

    public EmployeeEfficiencyModel() {

    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public double getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(double averageTime) {
        this.averageTime = averageTime;
    }
}
