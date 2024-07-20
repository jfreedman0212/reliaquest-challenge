package com.example.rqchallenge.employees.api;

import com.example.rqchallenge.employees.Employee;

import java.util.List;

public class EmployeeApiListResult {
    private String status;
    private List<Employee> data;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Employee> getData() {
        return data;
    }

    public void setData(List<Employee> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
