package com.example.rqchallenge.employees;

import com.example.rqchallenge.employees.api.IEmployeeApi;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final IEmployeeApi employeeApi;

    public EmployeeService(IEmployeeApi employeeApi) {
        this.employeeApi = employeeApi;
    }

    public List<Employee> getAllEmployees() {
        return getAllEmployees(null);
    }

    public List<Employee> getAllEmployees(String name) {
        final List<Employee> employees = employeeApi.getAllEmployees();

        if (Objects.isNull(name) || name.trim().isEmpty()) {
            return employees;
        }

        return employees
                .stream()
                .filter(e -> e.getEmployeeName().contains(name))
                .collect(Collectors.toList());
    }

    public Optional<Employee> getHighestPaidEmployee() {
        final List<Employee> employees = employeeApi.getAllEmployees();

        return employees.stream().max(Comparator.comparing(Employee::getEmployeeSalary));
    }

    public List<Employee> getTopEarners(int limit) {
        final List<Employee> employees = employeeApi.getAllEmployees();

        return employees
                .stream()
                .sorted(Comparator.comparing(Employee::getEmployeeSalary).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public Optional<Employee> getEmployeeById(String id) {
        return employeeApi.getEmployeeById(id);
    }
}
