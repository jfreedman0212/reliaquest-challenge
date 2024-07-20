package com.example.rqchallenge;

import com.example.rqchallenge.employees.Employee;
import com.example.rqchallenge.employees.EmployeeService;
import com.example.rqchallenge.employees.api.IEmployeeApi;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class EmployeeServiceTest {
    private EmployeeService employeeService;

    @BeforeEach
    public void setUpMockApi() {
        IEmployeeApi mockApi = Mockito.mock(IEmployeeApi.class);

        List<Employee> mockList = List.of(
                new Employee(1, "Bob Smith", BigDecimal.valueOf(200000, 2), 18, ""),
                new Employee(2, "Josh Freedman", BigDecimal.valueOf(300000, 2), 20, ""),
                new Employee(3, "Bob Robertson", BigDecimal.valueOf(100000, 2), 30, ""),
                new Employee(4, "Jane Smith", BigDecimal.valueOf(1, 2), 35, ""),
                new Employee(5, "Amaya Ashmore", BigDecimal.valueOf(1000000, 2), 50, ""),
                new Employee(6, "John Doe", BigDecimal.valueOf(2, 2), 65, ""),
                new Employee(7, "Toby Fox", BigDecimal.valueOf(500000, 2), 50, ""),
                new Employee(8, "Linus Torvalds", BigDecimal.valueOf(11100000, 2), 100, ""),
                new Employee(9, "Dan Abramov", BigDecimal.valueOf(300000, 2), 30, ""),
                new Employee(10, "Clark Kent", BigDecimal.valueOf(300000, 2), 1000, ""),
                new Employee(11, "Bob Smith II", BigDecimal.valueOf(212000, 2), 44, "")
        );

        Mockito.when(mockApi.getAllEmployees()).thenReturn(mockList);

        employeeService = new EmployeeService(mockApi);
    }

    @Test
    public void ReturnsAll() {
        // Act
        List<Employee> employees = employeeService.getAllEmployees();
        // Assert
        Assertions.assertEquals(employees.size(), 11);
    }

    @Test
    public void FiltersByName() {
        // Act
        List<Employee> employees = employeeService.getAllEmployees("Smith");
        // Assert
        Assertions.assertEquals(employees.size(), 3);
    }

    @Test
    public void GetsHighestSalary() {
        // Act
        Optional<Employee> highestPaidEmployee = employeeService.getHighestPaidEmployee();
        // Assert
        Assertions.assertTrue(highestPaidEmployee.isPresent());
        Assertions.assertEquals(highestPaidEmployee.get().getEmployeeName(), "Linus Torvalds");
    }

    @Test
    public void GetTopTenEarners() {
        // Act
        final int limit = 10;
        List<Employee> employees = employeeService.getTopEarners(limit);
        // Assert
        Assertions.assertEquals(employees.size(), limit);
        Assertions.assertEquals(employees.get(0).getEmployeeName(), "Linus Torvalds");
        // There are 11 list items, and John Doe is the second to last salary
        Assertions.assertEquals(employees.get(limit - 1).getEmployeeName(), "John Doe");
    }
}
