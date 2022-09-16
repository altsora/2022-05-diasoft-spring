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
import ru.diasoft.spring.employeeservice.model.request.AddTeamRequest;
import ru.diasoft.spring.employeeservice.model.response.AddTeamResponse;
import ru.diasoft.spring.employeeservice.service.TeamService;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TeamController.class)
@DisplayName("Тесты контроллера TeamController")
class TeamControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private TeamService teamService;

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(teamService);
    }

    @Test
    @DisplayName("Создание команды")
    void addTeamTest() throws Exception {
        final AddTeamRequest request = AddTeamRequest.builder()
                .name("123")
                .build();
        final AddTeamResponse response = new AddTeamResponse();
        response.setId(1);
        response.setUniqNumber(2);
        response.setName("123");
        response.setRetStatus(true);

        doReturn(response).when(teamService).addTeam(request);

        mvc.perform(post("/teams")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(response)))
                .andExpect(jsonPath("$.retStatus", is(true)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.uniqNumber", is(2)))
                .andExpect(jsonPath("$.name", is("123")))
        ;

        verify(teamService).addTeam(request);
    }
}