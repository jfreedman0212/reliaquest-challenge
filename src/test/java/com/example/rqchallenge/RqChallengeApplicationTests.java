package com.example.rqchallenge;

import com.example.rqchallenge.employees.Employee;
import com.example.rqchallenge.employees.api.IEmployeeApi;
import com.example.rqchallenge.employees.api.MockEmployeeApi;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RqChallengeApplicationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    // just a sanity check to make sure everything is running
    @Test
    public void getsEmptyEmployeeList() {
        // Act
        final ResponseEntity<List> response = restTemplate.getForEntity(String.format("http://localhost:%d", port), List.class);
        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(0, response.getBody().size());
    }


    @Test
    public void createReadThenDelete() {
        // first: create
        final Employee newEmployee = restTemplate.postForObject(
                String.format("http://localhost:%d", port),
                new Employee(null, "My New One", 1000, 22, ""),
                Employee.class
        );
        Assertions.assertNotNull(newEmployee);
        Assertions.assertNotNull(newEmployee.getId());
        // then: read
        final Employee foundEmployee = restTemplate.getForObject(
                String.format("http://localhost:%d/%d", port, newEmployee.getId()),
                Employee.class
        );
        Assertions.assertNotNull(foundEmployee);
        Assertions.assertEquals(foundEmployee.getId(), newEmployee.getId());
        // then: delete
        restTemplate.delete(String.format("http://localhost:%d/%d", port, newEmployee.getId()));
        // then: make sure it's gone
        final ResponseEntity<Employee> notFoundResponse = restTemplate.getForEntity(
                String.format("http://localhost:%d/%d", port, newEmployee.getId()),
                Employee.class
        );
        Assertions.assertEquals(notFoundResponse.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}
