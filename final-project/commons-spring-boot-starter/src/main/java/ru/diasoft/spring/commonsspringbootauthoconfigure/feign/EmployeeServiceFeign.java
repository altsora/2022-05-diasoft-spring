package ru.diasoft.spring.commonsspringbootauthoconfigure.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.request.LoginRequest;

@FeignClient(
        name = "${services.employee-service.name:employee-service}",
        url = "${services.employee-service.url:http://localhost:7082}/employees")
public interface EmployeeServiceFeign {
    @PostMapping("/login")
    Integer login(@RequestBody LoginRequest request);

    @GetMapping("/{employeeId}/exists")
    Boolean employeeExists(@PathVariable("employeeId") Integer employeeId);
}
