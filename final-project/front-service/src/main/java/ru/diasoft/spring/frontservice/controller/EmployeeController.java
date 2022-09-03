package ru.diasoft.spring.frontservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.diasoft.spring.frontservice.feign.EmployeeFeign;

@RestController
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeFeign employeeFeign;

    @GetMapping("/employee-service/employees/{id}")
    public Object getEmployeeById(@PathVariable("id") Integer employeeId) {
        return employeeFeign.getEmployeeById(employeeId);
    }
}
