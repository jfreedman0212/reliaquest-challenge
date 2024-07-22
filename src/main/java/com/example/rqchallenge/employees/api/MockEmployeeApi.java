package com.example.rqchallenge.employees.api;

import com.example.rqchallenge.employees.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MockEmployeeApi implements IEmployeeApi {
    private final AtomicInteger idCounter;
    private final ConcurrentHashMap<String, Employee> employeeMap;

    public MockEmployeeApi() {
        idCounter = new AtomicInteger(1);
        employeeMap = new ConcurrentHashMap<>();
    }

    @Override
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employeeMap.values());
    }

    @Override
    public Optional<Employee> getEmployeeById(String id) {
        return Optional.ofNullable(employeeMap.get(id));
    }

    @Override
    public Employee create(Employee employee) {
        final int nextId = idCounter.getAndAdd(1);
        employee.setId(nextId);
        employeeMap.put(Integer.toString(nextId), employee);

        return employee;
    }

    @Override
    public void delete(String id) {
        employeeMap.remove(id);
    }
}
