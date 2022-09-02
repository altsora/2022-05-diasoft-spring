package ru.diasoft.spring.employeeservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.diasoft.spring.employeeservice.model.request.AddEmployeeRequest;
import ru.diasoft.spring.employeeservice.model.request.UpdateEmployeeRequest;
import ru.diasoft.spring.employeeservice.model.response.AddEmployeeResponse;
import ru.diasoft.spring.employeeservice.model.response.GetEmployeeByIdResponse;
import ru.diasoft.spring.employeeservice.model.response.UpdateEmployeeResponse;
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

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GetEmployeeByIdResponse getEmployeeById(@PathVariable("id") Integer employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UpdateEmployeeResponse updateEmployee(@PathVariable("id") Integer employeeId,
                                                 @RequestBody @Valid UpdateEmployeeRequest request) {
        return employeeService.updateEmployee(employeeId, request);
    }

    @PutMapping("/{id}/activity")
    @ResponseStatus(HttpStatus.OK)
    public void setActivity(@PathVariable("id") Integer employeeId, @RequestParam("value") boolean value) {
        employeeService.setActivity(employeeId, value);
    }
}
