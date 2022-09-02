package ru.diasoft.spring.employeeservice.service;

import ru.diasoft.spring.employeeservice.model.request.AddEmployeeRequest;
import ru.diasoft.spring.employeeservice.model.request.UpdateEmployeeRequest;
import ru.diasoft.spring.employeeservice.model.response.AddEmployeeResponse;
import ru.diasoft.spring.employeeservice.model.response.GetEmployeeByIdResponse;
import ru.diasoft.spring.employeeservice.model.response.UpdateEmployeeResponse;

public interface EmployeeService {
    AddEmployeeResponse addEmployee(AddEmployeeRequest request);

    GetEmployeeByIdResponse getEmployeeById(Integer employeeId);

    UpdateEmployeeResponse updateEmployee(Integer employeeId, UpdateEmployeeRequest request);

    void setActivity(Integer employeeId, boolean value);
}
