package ru.diasoft.spring.employeeservice.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.diasoft.spring.employeeservice.domain.Team;
import ru.diasoft.spring.employeeservice.model.request.AddTeamRequest;
import ru.diasoft.spring.employeeservice.model.response.AddTeamResponse;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование маппера TeamMapper")
class TeamMapperTest {
    private final TeamMapper mapper = Mappers.getMapper(TeamMapper.class);

    @Test
    void fromDomainToAddTeamResponseTest() {
        final Team expected = Team.builder()
                .id(1)
                .uniqNumber(2)
                .name("name")
                .build();

        final AddTeamResponse actual = mapper.fromDomainToAddTeamResponse(expected);
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUniqNumber(), actual.getUniqNumber());
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    void fromAddTeamRequestToDomainTest() {
        final AddTeamRequest expected = AddTeamRequest.builder()
                .name("name")
                .build();

        final Team actual = mapper.fromAddTeamRequestToDomain(expected);
        assertNotNull(actual);
        assertEquals(expected.getName(), actual.getName());
    }
}