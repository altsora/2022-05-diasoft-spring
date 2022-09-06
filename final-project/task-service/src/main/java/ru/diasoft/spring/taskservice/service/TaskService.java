package ru.diasoft.spring.taskservice.service;

import ru.diasoft.spring.commonsspringbootauthoconfigure.model.response.BaseResponse;
import ru.diasoft.spring.taskservice.model.request.CreateTaskRequest;
import ru.diasoft.spring.taskservice.model.response.CreateTaskResponse;
import ru.diasoft.spring.taskservice.model.response.GetTasksByEmployeeResponse;

public interface TaskService {
    CreateTaskResponse createTask(CreateTaskRequest request);

    BaseResponse moveTask(Integer taskId, String sysName);

    BaseResponse deleteTask(Integer taskId);

    BaseResponse setExecutor(Integer taskId, Integer employeeId);

    GetTasksByEmployeeResponse getTasksByEmployee(Integer employeeId);
}
