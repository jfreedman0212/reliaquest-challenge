package com.example.rqchallenge.employees.api;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class EmployeeApiConfig {
    @Bean
    @Profile("!it")
    public IEmployeeApi employeeApi(final RestTemplateBuilder restTemplateBuilder) {
        return new EmployeeApi(restTemplateBuilder);
    }

    @Bean
    @Profile("it")
    public IEmployeeApi mockEmployeeApi() {
        return new MockEmployeeApi();
    }
}
