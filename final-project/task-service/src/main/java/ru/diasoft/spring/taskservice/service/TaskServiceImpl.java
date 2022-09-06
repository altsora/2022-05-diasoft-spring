package ru.diasoft.spring.taskservice.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.diasoft.spring.commonsspringbootauthoconfigure.aop.Loggable;
import ru.diasoft.spring.commonsspringbootauthoconfigure.exception.DomainNotFoundException;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.response.BaseResponse;
import ru.diasoft.spring.commonsspringbootauthoconfigure.utils.CommonUtils;
import ru.diasoft.spring.taskservice.domain.Task;
import ru.diasoft.spring.taskservice.enums.TaskState;
import ru.diasoft.spring.taskservice.mapper.TaskMapper;
import ru.diasoft.spring.taskservice.model.request.CreateTaskRequest;
import ru.diasoft.spring.taskservice.model.response.CreateTaskResponse;
import ru.diasoft.spring.taskservice.repository.TaskRepository;

import java.util.Optional;

import static ru.diasoft.spring.taskservice.utils.TaskServiceConstants.TASK_STATE_NOT_FOUND;

@Log4j2
@Loggable
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    /**
     * Создание задачи.
     *
     * @param request входные данные
     */
    @Override
    public CreateTaskResponse createTask(@NonNull CreateTaskRequest request) {
        final Optional<TaskState> taskState = TaskState.findByState(request.getState());
        if (taskState.isEmpty()) {
            final CreateTaskResponse response = new CreateTaskResponse();
            response.setRetMessage(String.format(TASK_STATE_NOT_FOUND, request.getState()));
            return response;
        }
        final Task domain = Task.builder()
                .title(CommonUtils.trimString(request.getTitle()))
                .state(taskState.get())
                .build();
        final Task saved = taskRepository.saveAndFlush(domain);
        final CreateTaskResponse response = taskMapper.fromDomainToCreateTaskResponse(saved);
        response.success();
        return response;
    }

    /**
     * Метод переводит задачу в новое состояние.
     *
     * @param taskId ID задачи
     * @param state  новое состояние задачи, в которое та должна попасть
     */
    @Override
    public BaseResponse moveTask(@NonNull Integer taskId, @NonNull String state) {
        final Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> DomainNotFoundException.id(Task.class, taskId));
        final Optional<TaskState> taskState = TaskState.findByState(state);
        if (taskState.isEmpty()) {
            return BaseResponse.createFail(String.format(TASK_STATE_NOT_FOUND, state));
        }
        task.setState(taskState.get());
        taskRepository.saveAndFlush(task);
        return BaseResponse.createSuccess();
    }

    /**
     * Удаление задачи.
     *
     * @param taskId ID задачи
     */
    @Override
    public BaseResponse deleteTask(@NonNull Integer taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw DomainNotFoundException.id(Task.class, taskId);
        }

        taskRepository.deleteById(taskId);
        return BaseResponse.createSuccess();
    }

    @Override
    public BaseResponse setExecutor(Integer taskId, Integer employeeId) {
        return null;
    }


}
