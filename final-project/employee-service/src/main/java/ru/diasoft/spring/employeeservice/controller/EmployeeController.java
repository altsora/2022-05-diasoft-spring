package ru.diasoft.spring.employeeservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.diasoft.spring.employeeservice.model.request.AddEmployeeRequest;
import ru.diasoft.spring.employeeservice.model.request.LoginRequest;
import ru.diasoft.spring.employeeservice.model.response.AddEmployeeResponse;
import ru.diasoft.spring.employeeservice.model.response.GetEmployeeByIdResponse;
import ru.diasoft.spring.employeeservice.model.response.SetEmployeeActivityResponse;
import ru.diasoft.spring.employeeservice.service.EmployeeService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddEmployeeResponse addEmployee(@RequestBody @Valid AddEmployeeRequest request) {
        return employeeService.addEmployee(request);
    }

    @GetMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public GetEmployeeByIdResponse getEmployeeById(@PathVariable("employeeId") Integer employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @PutMapping("/{employeeId}/activity")
    @ResponseStatus(HttpStatus.OK)
    public SetEmployeeActivityResponse setActivity(@PathVariable("employeeId") Integer employeeId,
                                                   @RequestParam("value") boolean value) {
        return employeeService.setActivity(employeeId, value);
    }

    @PostMapping("/login")
    public Integer login(@RequestBody LoginRequest request) {
        return employeeService.login(request);
    }

    @GetMapping("/{employeeId}/exists")
    public Boolean employeeExists(@PathVariable("employeeId") Integer employeeId) {
        return employeeService.employeeExists(employeeId);
    }
}
