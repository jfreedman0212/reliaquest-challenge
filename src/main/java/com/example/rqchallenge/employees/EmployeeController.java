package com.example.rqchallenge.employees;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class EmployeeController implements IEmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        return ResponseEntity.ok(employeeService.getAllEmployees(searchString));
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        return employeeService.getHighestPaidEmployee()
                .map(e -> ResponseEntity.ok(e.getEmployeeSalary()))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        final List<String> topEarningEmployeeNames = employeeService.getTopEarners(10)
                .stream()
                .map(Employee::getEmployeeName)
                .collect(Collectors.toList());

        return ResponseEntity.ok(topEarningEmployeeNames);
    }

    @Override
    public ResponseEntity<Employee> createEmployee(Employee employeeInput) {
        final Employee newEmployee = employeeService.create(employeeInput);

        return ResponseEntity.ok(newEmployee);
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        employeeService.delete(id);
        return ResponseEntity.ok("success");
    }
}
