package ru.diasoft.spring.employeeservice.controller;

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
import ru.diasoft.spring.employeeservice.model.request.AddEmployeeRequest;
import ru.diasoft.spring.employeeservice.model.request.LoginRequest;
import ru.diasoft.spring.employeeservice.model.response.AddEmployeeResponse;
import ru.diasoft.spring.employeeservice.model.response.GetEmployeeByIdResponse;
import ru.diasoft.spring.employeeservice.model.response.SetEmployeeActivityResponse;
import ru.diasoft.spring.employeeservice.model.response.TeamInfoForGetEmployeeByIdResponse;
import ru.diasoft.spring.employeeservice.service.EmployeeService;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
@DisplayName("Тесты контроллера EmployeeController")
class EmployeeControllerTest {
    public static final Integer ID = 1;
    public static final Integer UNIQ_NUMBER = 2;
    public static final String USERNAME = "username123";
    public static final String PASSWORD = "password123";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String MIDDLE_NAME = "middleName";
    public static final String FULL_NAME = "fullName";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private EmployeeService employeeService;

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    void addEmployeeTest() throws Exception {
        final AddEmployeeRequest request = AddEmployeeRequest.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .middleName(MIDDLE_NAME)
                .build();

        final BaseResponse response = new AddEmployeeResponse()
                .setId(ID)
                .setUniqNumber(UNIQ_NUMBER)
                .setUsername(USERNAME)
                .setFirstName(FIRST_NAME)
                .setLastName(LAST_NAME)
                .setMiddleName(MIDDLE_NAME)
                .setFullName(FULL_NAME)
                .setRetStatus(true);

        doReturn(response).when(employeeService).addEmployee(request);

        mvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(response)))
                .andExpect(jsonPath("$.retStatus", is(true)))
                .andExpect(jsonPath("$.id", is(ID)))
                .andExpect(jsonPath("$.uniqNumber", is(UNIQ_NUMBER)))
                .andExpect(jsonPath("$.username", is(USERNAME)))
                .andExpect(jsonPath("$.firstName", is(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$.middleName", is(MIDDLE_NAME)))
                .andExpect(jsonPath("$.fullName", is(FULL_NAME)))
        ;

        verify(employeeService).addEmployee(request);
    }

    @Test
    void getEmployeeByIdTest() throws Exception {
        final TeamInfoForGetEmployeeByIdResponse team = TeamInfoForGetEmployeeByIdResponse.builder()
                .id(10)
                .uniqNumber(20)
                .active(true)
                .name("team")
                .build();
        final BaseResponse response = new GetEmployeeByIdResponse()
                .setId(ID)
                .setUsername(USERNAME)
                .setUniqNumber(UNIQ_NUMBER)
                .setFirstName(FIRST_NAME)
                .setLastName(LAST_NAME)
                .setMiddleName(MIDDLE_NAME)
                .setFullName(FULL_NAME)
                .setActive(true)
                .setTeam(team)
                .setRetStatus(true);

        doReturn(response).when(employeeService).getEmployeeById(ID);

        mvc.perform(get("/employees/{employeeId}", ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(response)))
                .andExpect(jsonPath("$.retStatus", is(true)))
                .andExpect(jsonPath("$.id", is(ID)))
                .andExpect(jsonPath("$.uniqNumber", is(UNIQ_NUMBER)))
                .andExpect(jsonPath("$.username", is(USERNAME)))
                .andExpect(jsonPath("$.firstName", is(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$.middleName", is(MIDDLE_NAME)))
                .andExpect(jsonPath("$.fullName", is(FULL_NAME)))
                .andExpect(jsonPath("$.active", is(true)))
                .andExpect(jsonPath("$.team.id", is(10)))
                .andExpect(jsonPath("$.team.uniqNumber", is(20)))
                .andExpect(jsonPath("$.team.active", is(true)))
                .andExpect(jsonPath("$.team.name", is("team")))
        ;

        verify(employeeService).getEmployeeById(ID);
    }

    @Test
    void setActivityTest() throws Exception {
        final BaseResponse response = new SetEmployeeActivityResponse()
                .setActivity(true)
                .setRetStatus(true);

        doReturn(response).when(employeeService).setActivity(ID, true);

        mvc.perform(put("/employees/{employeeId}/activity", ID)
                .param("value", "true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(response)))
                .andExpect(jsonPath("$.retStatus", is(true)))
                .andExpect(jsonPath("$.activity", is(true)))
        ;

        verify(employeeService).setActivity(ID, true);
    }

    @Test
    void loginTest() throws Exception {
        final LoginRequest request = LoginRequest.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();
        final Integer response = ID;

        doReturn(response).when(employeeService).login(request);

        mvc.perform(post("/employees/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(response)))
        ;

        verify(employeeService).login(request);
    }

    @Test
    void employeeExistsTest() throws Exception {
        doReturn(true).when(employeeService).employeeExists(ID);

        mvc.perform(get("/employees/{employeeId}/exists", ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));

        verify(employeeService).employeeExists(ID);
    }
}