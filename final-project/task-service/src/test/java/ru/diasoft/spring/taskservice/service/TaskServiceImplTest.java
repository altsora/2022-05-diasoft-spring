package ru.diasoft.spring.taskservice.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.diasoft.spring.commonsspringbootauthoconfigure.exception.DomainNotFoundException;
import ru.diasoft.spring.commonsspringbootauthoconfigure.feign.EmployeeServiceFeign;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.response.BaseResponse;
import ru.diasoft.spring.kafkaservice.utils.KafkaProducer;
import ru.diasoft.spring.taskservice.domain.Task;
import ru.diasoft.spring.taskservice.enums.TaskState;
import ru.diasoft.spring.taskservice.mapper.TaskMapper;
import ru.diasoft.spring.taskservice.model.request.CreateTaskRequest;
import ru.diasoft.spring.taskservice.model.response.CreateTaskResponse;
import ru.diasoft.spring.taskservice.model.response.GetTasksByEmployeeResponse;
import ru.diasoft.spring.taskservice.repository.TaskEmployeeLinkRepository;
import ru.diasoft.spring.taskservice.repository.TaskRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Тестирование сервиса задач TaskServiceImpl")
class TaskServiceImplTest {
    private final TaskRepository taskRepository = mock(TaskRepository.class);
    private final TaskEmployeeLinkRepository taskEmployeeLinkRepository = mock(TaskEmployeeLinkRepository.class);
    private final TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);
    private final KafkaProducer kafkaProducer = mock(KafkaProducer.class);
    private final EmployeeServiceFeign employeeServiceFeign = mock(EmployeeServiceFeign.class);
    private final TaskService service = new TaskServiceImpl(
            taskRepository,
            taskEmployeeLinkRepository,
            taskMapper,
            kafkaProducer,
            employeeServiceFeign
    );

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(taskRepository);
        verifyNoMoreInteractions(taskEmployeeLinkRepository);
        verifyNoMoreInteractions(kafkaProducer);
        verifyNoMoreInteractions(employeeServiceFeign);
    }

    @Test
    @DisplayName("Создание задачи")
    void createTask() {
        final CreateTaskRequest request = CreateTaskRequest.builder()
                .title("title")
                .build();

        doReturn(new Task()).when(taskRepository).saveAndFlush(any(Task.class));

        final CreateTaskResponse response = service.createTask(request);
        assertNotNull(response);
        assertTrue(response.isSuccess());

        verify(taskRepository).saveAndFlush(any(Task.class));
    }

    @Test
    @DisplayName("Установка исполнителя задачи: сотрудник не найден")
    void setExecutor_employeeNotFound() {
        final Integer taskId = 1;
        final Integer employeeId = 2;

        doReturn(false).when(employeeServiceFeign).employeeExists(employeeId);

        final BaseResponse response = service.setExecutor(taskId, employeeId);
        assertNotNull(response);
        assertTrue(response.isFail());
        assertNotNull(response.getRetMessage());

        verify(employeeServiceFeign).employeeExists(employeeId);
    }

    @Test
    @DisplayName("Установка исполнителя задачи: задача не найдена")
    void setExecutor_taskNotFound() {
        final Integer taskId = 1;
        final Integer employeeId = 2;

        doReturn(true).when(employeeServiceFeign).employeeExists(employeeId);
        doReturn(Optional.empty()).when(taskRepository).findById(taskId);

        assertThrows(DomainNotFoundException.class, () -> service.setExecutor(taskId, employeeId));

        verify(employeeServiceFeign).employeeExists(employeeId);
        verify(taskRepository).findById(taskId);
    }

    @Test
    @DisplayName("Установка исполнителя задачи: с задачи удалён исполнитель, на которой и так нет исполнителя")
    void setExecutor_taskHasNotAlreadyExecutor() {
        final Integer taskId = 1;
        final Integer employeeId = null;
        final Task task = Task.builder().build();

        doReturn(true).when(employeeServiceFeign).employeeExists(employeeId);
        doReturn(Optional.of(task)).when(taskRepository).findById(taskId);
        doReturn(Optional.empty()).when(taskEmployeeLinkRepository).findEmployeeIdByTaskId(taskId);

        final BaseResponse response = service.setExecutor(taskId, employeeId);
        assertNotNull(response);
        assertTrue(response.isFail());
        assertNotNull(response.getRetMessage());

        verify(taskRepository).findById(taskId);
        verify(taskEmployeeLinkRepository).findEmployeeIdByTaskId(taskId);
    }

    @Test
    @DisplayName("Получить информацию о задачах сотрудника")
    void getTasksByEmployee_employeeIsNotNull() {
        final Integer employeeId = 1;
        final Task task = Task.builder()
                .state(TaskState.NEW)
                .build();

        doReturn(Collections.singletonList(task)).when(taskRepository).findAllTasksByEmployeeId(employeeId);

        final GetTasksByEmployeeResponse response = service.getTasksByEmployee(employeeId);
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertNotNull(response.getEmployeeId());
        assertNotNull(response.getTasks());
        assertFalse(response.getTasks().isEmpty());

        verify(taskRepository).findAllTasksByEmployeeId(employeeId);
    }

    @Test
    @DisplayName("Получить информацию о задачах без исполнителя")
    void getTasksByEmployee_employeeIsNull() {
        final Integer employeeId = null;
        final Task task = Task.builder()
                .state(TaskState.NEW)
                .build();

        doReturn(Collections.singletonList(task)).when(taskRepository).findAllTasksWithoutEmployee();

        final GetTasksByEmployeeResponse response = service.getTasksByEmployee(employeeId);
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertNull(response.getEmployeeId());
        assertNotNull(response.getTasks());
        assertFalse(response.getTasks().isEmpty());

        verify(taskRepository).findAllTasksWithoutEmployee();
    }
}