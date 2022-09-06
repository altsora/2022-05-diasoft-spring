package ru.diasoft.spring.taskservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.response.BaseResponse;
import ru.diasoft.spring.taskservice.model.request.CreateTaskRequest;
import ru.diasoft.spring.taskservice.model.response.CreateTaskResponse;
import ru.diasoft.spring.taskservice.model.response.GetTasksByEmployeeResponse;
import ru.diasoft.spring.taskservice.service.TaskService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateTaskResponse createTask(@RequestBody CreateTaskRequest request) {
        return taskService.createTask(request);
    }

    @PutMapping("/{taskId}/move")
    public BaseResponse moveTask(@PathVariable("taskId") Integer taskId, @RequestParam("sysNameState") String sysNameState) {
        return taskService.moveTask(taskId, sysNameState);
    }

    @DeleteMapping("/{taskId}")
    public BaseResponse deleteTask(@PathVariable("taskId") Integer taskId) {
        return taskService.deleteTask(taskId);
    }

    @GetMapping("/employee/{employeeId}")
    public GetTasksByEmployeeResponse getTasksByEmployee(@PathVariable("employeeId") Integer employeeId) {
        return taskService.getTasksByEmployee(employeeId);
    }
}
