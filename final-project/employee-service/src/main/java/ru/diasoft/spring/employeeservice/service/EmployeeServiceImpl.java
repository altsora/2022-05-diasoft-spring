package ru.diasoft.spring.employeeservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.diasoft.spring.employeeservice.model.request.AddEmployeeRequest;
import ru.diasoft.spring.employeeservice.model.AddEmployeeResponse;
import ru.diasoft.spring.employeeservice.repository.EmployeeRepository;

@Log4j2
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public AddEmployeeResponse addEmployee(AddEmployeeRequest request) {
        System.err.println(request);
        return null;
    }
}
