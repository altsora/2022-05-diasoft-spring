package ru.diasoft.spring.frontservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.diasoft.spring.frontservice.model.LoginRequest;

@FeignClient(
        name = "${services.employee-service.name}",
        url = "${services.employee-service.url}/employees")
public interface EmployeeFeign {
    @GetMapping("/{id}")
    Object getEmployeeById(@PathVariable("id") Integer employeeId);

    @PostMapping("/login")
    Integer login(@RequestBody LoginRequest request);
}
