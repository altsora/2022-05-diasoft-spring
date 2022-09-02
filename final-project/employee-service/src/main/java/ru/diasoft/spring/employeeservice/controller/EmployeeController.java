package ru.diasoft.spring.employeeservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.diasoft.spring.employeeservice.model.request.AddEmployeeRequest;
import ru.diasoft.spring.employeeservice.model.AddEmployeeResponse;
import ru.diasoft.spring.employeeservice.service.EmployeeService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddEmployeeResponse addEmployee(@RequestBody @Valid AddEmployeeRequest request) {
        return employeeService.addEmployee(request);
    }
}
