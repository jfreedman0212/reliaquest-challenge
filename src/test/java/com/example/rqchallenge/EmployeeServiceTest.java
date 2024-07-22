package com.example.rqchallenge;

import com.example.rqchallenge.employees.Employee;
import com.example.rqchallenge.employees.EmployeeService;
import com.example.rqchallenge.employees.api.IEmployeeApi;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

public class EmployeeServiceTest {
    private EmployeeService employeeService;

    @BeforeEach
    public void setUpMockApi() {
        IEmployeeApi mockApi = Mockito.mock(IEmployeeApi.class);

        List<Employee> mockList = List.of(
                new Employee(1, "Bob Smith", 200000, 18, ""),
                new Employee(2, "Josh Freedman", 300000, 20, ""),
                new Employee(3, "Bob Robertson", 100000, 30, ""),
                new Employee(4, "Jane Smith", 1, 35, ""),
                new Employee(5, "Amaya Ashmore", 1000000, 50, ""),
                new Employee(6, "John Doe", 2, 65, ""),
                new Employee(7, "Toby Fox", 500000, 50, ""),
                new Employee(8, "Linus Torvalds", 11100000, 100, ""),
                new Employee(9, "Dan Abramov", 300000, 30, ""),
                new Employee(10, "Clark Kent", 300000, 1000, ""),
                new Employee(11, "Bob Smith II", 212000, 44, "")
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
