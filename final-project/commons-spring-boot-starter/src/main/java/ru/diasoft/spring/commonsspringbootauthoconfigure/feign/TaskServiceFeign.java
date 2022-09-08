package ru.diasoft.spring.commonsspringbootauthoconfigure.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.diasoft.spring.commonsspringbootauthoconfigure.aop.Loggable;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.request.CreateTaskRequest;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.response.BaseResponse;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.response.CreateTaskResponse;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.response.GetTasksByEmployeeResponse;

@FeignClient(
        name = "${services.task-service.name:task-service}",
        url = "${services.task-service.url:http://localhost:7081}")
@Loggable
public interface TaskServiceFeign {
    @GetMapping("/tasks/employee")
    GetTasksByEmployeeResponse getTasksByEmployee(@RequestParam(value = "employeeId", required = false) Integer employeeId);

    @PostMapping("/tasks/{taskId}/employee/executor")
    BaseResponse setExecutor(@PathVariable("taskId") Integer taskId,
                             @RequestParam(value = "employeeId", required = false) Integer employeeId);

    @PostMapping("/tasks")
    CreateTaskResponse createTask(@RequestBody CreateTaskRequest request);
}
