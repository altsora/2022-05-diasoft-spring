package ru.diasoft.spring.frontservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "${services.employee-service.name}",
        url = "${services.employee-service.url}/employees")
public interface EmployeeFeign {
    @GetMapping("{id}")
    Object getEmployeeById(@PathVariable("id") Integer employeeId);
}
