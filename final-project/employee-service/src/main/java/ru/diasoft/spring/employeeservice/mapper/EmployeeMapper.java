package ru.diasoft.spring.employeeservice.mapper;

import org.mapstruct.Mapper;
import ru.diasoft.spring.employeeservice.domain.Employee;
import ru.diasoft.spring.employeeservice.model.request.AddEmployeeRequest;
import ru.diasoft.spring.employeeservice.model.response.AddEmployeeResponse;
import ru.diasoft.spring.employeeservice.model.response.GetEmployeeByIdResponse;
import ru.diasoft.spring.employeeservice.model.response.UpdateEmployeeResponse;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    GetEmployeeByIdResponse fromDomainToGetEmployeeByIdResponse(Employee domain);

    Employee fromAddEmployeeRequestToDomain(AddEmployeeRequest request);

    AddEmployeeResponse fromDomainToAddEmployeeResponse(Employee domain);

    UpdateEmployeeResponse fromDomainToUpdateEmployeeResponse(Employee domain);
}
