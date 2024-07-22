package com.example.rqchallenge.employees.api;

import com.example.rqchallenge.employees.Employee;

import java.util.List;
import java.util.Optional;

/**
 * Interface for interacting with the Dummy Rest API to work with Employees
 */
public interface IEmployeeApi {
    /**
     * Retrieves every employee in the API.
     * @return List of Employees
     */
    List<Employee> getAllEmployees();

    /**
     * Retrieves a single employee by ID.
     * @param id the ID of the employee
     * @return a filled optional if an employee exists, otherwise an empty one
     */
    Optional<Employee> getEmployeeById(String id);

    /**
     * Creates a new employee with the provided data
     * @param employee the populated employee to create
     */
    Employee create(Employee employee);

    /**
     * Deletes an employee by ID
     * @param id the ID of the employee to delete
     */
    void delete(String id);
}
