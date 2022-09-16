package ru.diasoft.spring.employeeservice.service;

import ru.diasoft.spring.employeeservice.model.request.AddEmployeeRequest;
import ru.diasoft.spring.employeeservice.model.request.LoginRequest;
import ru.diasoft.spring.employeeservice.model.response.AddEmployeeResponse;
import ru.diasoft.spring.employeeservice.model.response.GetEmployeeByIdResponse;
import ru.diasoft.spring.employeeservice.model.response.SetEmployeeActivityResponse;

public interface EmployeeService {
    AddEmployeeResponse addEmployee(AddEmployeeRequest request);

    GetEmployeeByIdResponse getEmployeeById(Integer employeeId);

    SetEmployeeActivityResponse setActivity(Integer employeeId, boolean value);

    Integer login(LoginRequest request);

    Boolean employeeExists(Integer employeeId);
}
