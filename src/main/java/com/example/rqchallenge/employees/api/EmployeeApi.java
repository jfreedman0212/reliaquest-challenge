package com.example.rqchallenge.employees.api;

import com.example.rqchallenge.employees.Employee;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class EmployeeApi implements IEmployeeApi {
    private final RestTemplate restTemplate;

    public EmployeeApi(final RestTemplateBuilder builder) {
        this.restTemplate = builder
                .rootUri("https://dummy.restapiexample.com/api/v1")
                .build();
    }

    @Override
    public List<Employee> getAllEmployees() {
        final EmployeeApiListResult result = Objects.requireNonNullElse(
                restTemplate.getForObject("/employees", EmployeeApiListResult.class),
                new EmployeeApiListResult()
        );

        return Objects.requireNonNullElse(result.getData(), List.of());
    }

    @Override
    public Optional<Employee> getEmployeeById(String id) {
        final EmployeeApiSingleResult result = Objects.requireNonNullElse(
                restTemplate.getForObject("/employee/{id}", EmployeeApiSingleResult.class, id),
                new EmployeeApiSingleResult()
        );

        return Optional.ofNullable(result.getData());
    }

    @Override
    public Employee create(Employee employee) {
        final EmployeeApiSingleResult result = restTemplate.postForObject(
                "/create",
                employee,
                EmployeeApiSingleResult.class
        );

        return Objects.requireNonNull(result).getData();
    }

    @Override
    public void delete(String id) {
        restTemplate.delete("/delete/{id}", id);
    }
}
