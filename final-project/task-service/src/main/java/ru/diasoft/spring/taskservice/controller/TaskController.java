package ru.diasoft.spring.taskservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.response.BaseResponse;
import ru.diasoft.spring.taskservice.model.request.CreateTaskRequest;
import ru.diasoft.spring.taskservice.model.response.CreateTaskResponse;
import ru.diasoft.spring.taskservice.model.response.GetTasksByEmployeeResponse;
import ru.diasoft.spring.taskservice.service.TaskService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateTaskResponse createTask(@RequestBody @Valid CreateTaskRequest request) {
        return taskService.createTask(request);
    }

    @GetMapping("/employee")
    public GetTasksByEmployeeResponse getTasksByEmployee(@RequestParam(value = "employeeId", required = false) Integer employeeId) {
        return taskService.getTasksByEmployee(employeeId);
    }

    @PostMapping("/{taskId}/employee/executor")
    public BaseResponse setExecutor(@PathVariable("taskId") Integer taskId,
                                    @RequestParam(value = "employeeId", required = false) Integer employeeId) {
        return taskService.setExecutor(taskId, employeeId);
    }
}
