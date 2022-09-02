package ru.diasoft.spring.employeeservice.service;

import ru.diasoft.spring.employeeservice.model.request.AddEmployeeRequest;
import ru.diasoft.spring.employeeservice.model.AddEmployeeResponse;

public interface EmployeeService {
    AddEmployeeResponse addEmployee(AddEmployeeRequest request);
}
