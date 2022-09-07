package ru.diasoft.spring.commonsspringbootauthoconfigure.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.diasoft.spring.commonsspringbootauthoconfigure.aop.Loggable;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.request.LoginRequest;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.response.GetEmployeeByIdResponse;

@FeignClient(
        name = "${services.employee-service.name:employee-service}",
        url = "${services.employee-service.url:http://localhost:7082}")
@Loggable
public interface EmployeeServiceFeign {
    @PostMapping("/employees/login")
    Integer login(@RequestBody LoginRequest request);

    @GetMapping("/employees/{employeeId}/exists")
    Boolean employeeExists(@PathVariable("employeeId") Integer employeeId);

    @GetMapping("/employees/{employeeId}")
    GetEmployeeByIdResponse getEmployeeById(@PathVariable("employeeId") Integer employeeId);
}
