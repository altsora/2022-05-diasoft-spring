package ru.diasoft.spring.taskservice.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.spring.commonsspringbootauthoconfigure.aop.Loggable;
import ru.diasoft.spring.commonsspringbootauthoconfigure.exception.DomainNotFoundException;
import ru.diasoft.spring.commonsspringbootauthoconfigure.feign.EmployeeServiceFeign;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.response.BaseResponse;
import ru.diasoft.spring.commonsspringbootauthoconfigure.utils.CommonUtils;
import ru.diasoft.spring.kafkaservice.data.EmployeeNotificationPayload;
import ru.diasoft.spring.kafkaservice.utils.KafkaProducer;
import ru.diasoft.spring.kafkaservice.utils.TopicNameConstants;
import ru.diasoft.spring.taskservice.domain.Task;
import ru.diasoft.spring.taskservice.domain.TaskEmployeeLink;
import ru.diasoft.spring.taskservice.enums.TaskState;
import ru.diasoft.spring.taskservice.mapper.TaskMapper;
import ru.diasoft.spring.taskservice.model.request.CreateTaskRequest;
import ru.diasoft.spring.taskservice.model.response.CreateTaskResponse;
import ru.diasoft.spring.taskservice.model.response.GetTasksByEmployeeResponse;
import ru.diasoft.spring.taskservice.model.response.TaskForGetTasksByEmployeeResponse;
import ru.diasoft.spring.taskservice.repository.TaskEmployeeLinkRepository;
import ru.diasoft.spring.taskservice.repository.TaskRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.diasoft.spring.taskservice.utils.TaskServiceConstants.*;

@Log4j2
@Loggable
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskEmployeeLinkRepository taskEmployeeLinkRepository;
    private final TaskMapper taskMapper;
    private final KafkaProducer kafkaProducer;
    private final EmployeeServiceFeign employeeServiceFeign;

    /**
     * Создание задачи.
     *
     * @param request входные данные
     */
    @Override
    @Transactional
    public CreateTaskResponse createTask(@NonNull CreateTaskRequest request) {
        final Optional<TaskState> taskState = TaskState.findBySysName(request.getState());
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
    @Transactional
    public BaseResponse moveTask(@NonNull Integer taskId, @NonNull String state) {
        final Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> DomainNotFoundException.id(Task.class, taskId));
        final Optional<TaskState> taskState = TaskState.findBySysName(state);
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
    @Transactional
    public BaseResponse deleteTask(@NonNull Integer taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw DomainNotFoundException.id(Task.class, taskId);
        }

        taskRepository.deleteById(taskId);
        return BaseResponse.createSuccess();
    }

    /**
     * Устанавливает исполнителя задачи.
     * <p>
     * Если сотрудник не указан, то с задачи удаляется текущий исполнитель и он получает уведомление.
     * <p>
     * Когда исполнитель установлен, он получает уведомление о новой задаче.
     *
     * @param taskId     ID задачи
     * @param employeeId ID сотрудника
     */
    @Override
    @Transactional
    public BaseResponse setExecutor(@NonNull Integer taskId, Integer employeeId) {
        final Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> DomainNotFoundException.id(Task.class, taskId));
        final boolean newEmployeeIsNull = employeeId == null;
        if (!newEmployeeIsNull) {
            final Boolean employeeExists = employeeServiceFeign.employeeExists(employeeId);
            if (Boolean.FALSE.equals(employeeExists)) {
                return BaseResponse.createFail(String.format(EMPLOYEE_BY_ID_NOT_FOUND, employeeId));
            }
        }

        final Optional<Integer> currentEmployeeIdOpt = taskEmployeeLinkRepository.findEmployeeIdByTaskId(taskId);
        if (currentEmployeeIdOpt.isPresent()) {
            final Integer currentEmployeeId = currentEmployeeIdOpt.get();
            if (currentEmployeeId.equals(employeeId)) {
                return BaseResponse.createFail(String.format(TASK_HAS_ALREADY_EXECUTOR, task.getId(), currentEmployeeId));
            }
            removeExecutorAndNotice(task, currentEmployeeId);
            if (!newEmployeeIsNull) {
                setExecutorAndNotice(task, employeeId);
            }
            return BaseResponse.createSuccess();
        }

        if (newEmployeeIsNull) {
            return BaseResponse.createFail(String.format(TASK_HAS_NO_ALREADY_EXECUTOR, task.getId()));
        }
        setExecutorAndNotice(task, employeeId);
        return BaseResponse.createSuccess();
    }

    /**
     * Удаляет исполнителя на задаче и уведомляет его об этом
     *
     * @param task       задача, с которой удаляется исполнитель
     * @param employeeId ID сотрудника, который освобождается от задачи
     */
    private void removeExecutorAndNotice(@NonNull Task task, @NonNull Integer employeeId) {
        taskEmployeeLinkRepository.deleteByTaskAndEmployee(task.getId(), employeeId);
        final EmployeeNotificationPayload payload = EmployeeNotificationPayload.builder()
                .employeeId(employeeId)
                .taskId(task.getId())
                .message(String.format(EMPLOYEE_IS_NOT_EXECUTOR_OF_TASK, task.getUniqNumber(), task.getTitle()))
                .build();
        kafkaProducer.send(TopicNameConstants.TOPIC_NAME_EMPLOYEE_NOTIFICATION, payload);
    }

    /**
     * Устанавливает нового исполнителя задачи и уведомляет его об этом
     *
     * @param task          задача, которой устанавливается исполнитель
     * @param newEmployeeId ID сотрудника, который получает задачу
     */
    private void setExecutorAndNotice(@NonNull Task task,
                                      @NonNull Integer newEmployeeId) {
        final TaskEmployeeLink link = TaskEmployeeLink.builder()
                .taskId(task.getId())
                .employeeId(newEmployeeId)
                .build();
        taskEmployeeLinkRepository.saveAndFlush(link);
        final EmployeeNotificationPayload payload = EmployeeNotificationPayload.builder()
                .employeeId(newEmployeeId)
                .taskId(task.getId())
                .message(String.format(EMPLOYEE_IS_EXECUTOR_OF_TASK, task.getUniqNumber(), task.getTitle()))
                .build();
        kafkaProducer.send(TopicNameConstants.TOPIC_NAME_EMPLOYEE_NOTIFICATION, payload);
    }

    /**
     * Возвращает информацию о задачах по указанному сотруднику
     *
     * @param employeeId ID сотрудника
     */
    @Override
    public GetTasksByEmployeeResponse getTasksByEmployee(@NonNull Integer employeeId) {
        final List<TaskForGetTasksByEmployeeResponse> tasks = taskRepository.findAllTasksByEmployeeId(employeeId)
                .stream()
                .map(taskMapper::fromDomainToTaskForGetTasksByEmployeeResponse)
                .collect(Collectors.toList());
        if (tasks.isEmpty()) {
            final GetTasksByEmployeeResponse response = new GetTasksByEmployeeResponse();
            response.setRetMessage(String.format(EMPLOYEE_HAS_NO_TASKS, employeeId));
            return response;
        }
        final GetTasksByEmployeeResponse response = GetTasksByEmployeeResponse.builder()
                .employeeId(employeeId)
                .tasks(tasks)
                .build();
        response.success();
        return response;
    }


}
