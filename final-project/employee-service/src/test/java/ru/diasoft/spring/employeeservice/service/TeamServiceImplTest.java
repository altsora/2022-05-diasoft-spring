package ru.diasoft.spring.employeeservice.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.diasoft.spring.employeeservice.domain.Team;
import ru.diasoft.spring.employeeservice.mapper.TeamMapper;
import ru.diasoft.spring.employeeservice.model.request.AddTeamRequest;
import ru.diasoft.spring.employeeservice.model.response.AddTeamResponse;
import ru.diasoft.spring.employeeservice.repository.TeamRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@DisplayName("Тестирование сервиса команд TeamServiceImpl")
class TeamServiceImplTest {
    private final TeamRepository teamRepository = mock(TeamRepository.class);
    private final TeamMapper teamMapper = Mappers.getMapper(TeamMapper.class);
    private final TeamService service = new TeamServiceImpl(
            teamRepository,
            teamMapper
    );

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(teamRepository);
    }

    @Test
    @DisplayName("Добавление команды: успешно")
    void addTeamOkTest() {
        final AddTeamRequest request = AddTeamRequest.builder()
                .name("name")
                .build();
        final Team saved = new Team();

        doReturn(false).when(teamRepository).existsByName(request.getName());
        doReturn(saved).when(teamRepository).saveAndFlush(any(Team.class));

        final AddTeamResponse response = service.addTeam(request);
        assertNotNull(response);
        assertTrue(response.isSuccess());

        verify(teamRepository).existsByName(request.getName());
        verify(teamRepository).saveAndFlush(any(Team.class));
    }
}