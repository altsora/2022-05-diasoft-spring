package ru.diasoft.spring.employeeservice.mapper;

import org.mapstruct.Mapper;
import ru.diasoft.spring.employeeservice.domain.Team;
import ru.diasoft.spring.employeeservice.model.request.AddTeamRequest;
import ru.diasoft.spring.employeeservice.model.response.AddTeamResponse;
import ru.diasoft.spring.employeeservice.model.response.GetTeamByUniqNumberResponse;
import ru.diasoft.spring.employeeservice.model.response.SetTeamActivityResponse;
import ru.diasoft.spring.employeeservice.model.response.TeamInfoForGetEmployeeByIdResponse;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    AddTeamResponse fromDomainToAddTeamResponse(Team domain);

    GetTeamByUniqNumberResponse fromDomainToGetTeamByUniqNumberResponse(Team domain);

    SetTeamActivityResponse fromDomainToSetTeamActivityResponse(Team domain);

    Team fromAddTeamRequestToDomain(AddTeamRequest request);

    TeamInfoForGetEmployeeByIdResponse fromDomainToTeamInfoForGetEmployeeByIdResponse(Team domain);
}
