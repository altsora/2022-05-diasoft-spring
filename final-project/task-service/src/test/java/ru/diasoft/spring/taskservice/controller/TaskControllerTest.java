package ru.diasoft.spring.taskservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.response.BaseResponse;
import ru.diasoft.spring.taskservice.enums.TaskState;
import ru.diasoft.spring.taskservice.model.request.CreateTaskRequest;
import ru.diasoft.spring.taskservice.model.response.CreateTaskResponse;
import ru.diasoft.spring.taskservice.model.response.GetTasksByEmployeeResponse;
import ru.diasoft.spring.taskservice.model.response.TaskForGetTasksByEmployeeResponse;
import ru.diasoft.spring.taskservice.service.TaskService;

import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@DisplayName("Тесты контроллера TaskController")
class TaskControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private TaskService service;

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(service);
    }

    @Test
    void createTaskTest() throws Exception {
        final CreateTaskRequest request = CreateTaskRequest.builder()
                .title("title")
                .build();
        final BaseResponse response = new CreateTaskResponse()
                .setId(1)
                .setTitle("title")
                .setState(TaskState.NEW.getState())
                .setUniqNumber(2)
                .setRetStatus(true);

        doReturn(response).when(service).createTask(request);

        mvc.perform(post("/tasks").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(response)))
                .andExpect(jsonPath("$.retStatus", is(true)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.uniqNumber", is(2)))
                .andExpect(jsonPath("$.title", is("title")))
                .andExpect(jsonPath("$.state", is("Зарегистрировано")))
        ;

        verify(service).createTask(request);
    }

    @Test
    void getTasksByEmployeeTest() throws Exception {
        final Integer employeeId = 1;
        final BaseResponse response = new GetTasksByEmployeeResponse()
                .setEmployeeId(employeeId)
                .setTasks(Collections.emptyList())
                .setRetStatus(true);

        doReturn(response).when(service).getTasksByEmployee(employeeId);

        mvc.perform(get("/tasks/employee")
                .param("employeeId", employeeId.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(response)))
                .andExpect(jsonPath("$.retStatus", is(true)))
                .andExpect(jsonPath("$.employeeId", is(1)))
                .andExpect(jsonPath("$.tasks", empty()));

        verify(service).getTasksByEmployee(employeeId);
    }

    @Test
    void setExecutorTest() throws Exception {
        final Integer taskId = 1;
        final Integer employeeId = null;
        final BaseResponse response = BaseResponse.createSuccess();

        doReturn(response).when(service).setExecutor(taskId, employeeId);

        mvc.perform(post("/tasks/{taskId}/employee/executor", taskId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(response)))
                .andExpect(jsonPath("$.retStatus", is(true)))
        ;

        verify(service).setExecutor(taskId, employeeId);
    }
}