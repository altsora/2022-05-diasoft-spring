package ru.diasoft.spring.employeeservice.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.diasoft.spring.employeeservice.domain.Employee;
import ru.diasoft.spring.employeeservice.domain.Team;
import ru.diasoft.spring.employeeservice.model.request.AddEmployeeRequest;
import ru.diasoft.spring.employeeservice.model.response.AddEmployeeResponse;
import ru.diasoft.spring.employeeservice.model.response.GetEmployeeByIdResponse;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование маппера EmployeeMapper")
class EmployeeMapperTest {
    private final EmployeeMapper mapper = Mappers.getMapper(EmployeeMapper.class);

    @Test
    void fromDomainToGetEmployeeByIdResponseTest() {
        final Team team = Team.builder()
                .id(10)
                .name("name")
                .uniqNumber(11)
                .active(false)
                .build();
        final Employee expected = Employee.builder()
                .id(1)
                .username("username")
                .uniqNumber(2)
                .firstName("first")
                .lastName("last")
                .middleName("middle")
                .active(true)
                .team(team)
                .build();

        final GetEmployeeByIdResponse actual = mapper.fromDomainToGetEmployeeByIdResponse(expected);
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getUniqNumber(), actual.getUniqNumber());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getMiddleName(), actual.getMiddleName());
        assertEquals(expected.isActive(), actual.isActive());
        assertEquals(expected.getTeam().getId(), actual.getTeam().getId());
        assertEquals(expected.getTeam().getName(), actual.getTeam().getName());
        assertEquals(expected.getTeam().getUniqNumber(), actual.getTeam().getUniqNumber());
        assertEquals(expected.getTeam().isActive(), actual.getTeam().isActive());
    }

    @Test
    void fromAddEmployeeRequestToDomainTest() {
        final AddEmployeeRequest expected = AddEmployeeRequest.builder().build();

        final Employee actual = mapper.fromAddEmployeeRequestToDomain(expected);
        assertNotNull(actual);
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getMiddleName(), actual.getMiddleName());
    }

    @Test
    void fromDomainToAddEmployeeResponseTest() {
        final Employee expected = Employee.builder().build();

        final AddEmployeeResponse actual = mapper.fromDomainToAddEmployeeResponse(expected);
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getUniqNumber(), actual.getUniqNumber());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getMiddleName(), actual.getMiddleName());
    }
}