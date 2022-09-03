package ru.diasoft.spring.employeeservice.service;

import ru.diasoft.spring.employeeservice.model.request.AddTeamRequest;
import ru.diasoft.spring.employeeservice.model.response.AddTeamResponse;
import ru.diasoft.spring.employeeservice.model.response.GetTeamByUniqNumberResponse;
import ru.diasoft.spring.employeeservice.model.response.SetTeamActivityResponse;

public interface TeamService {
    AddTeamResponse addTeam(AddTeamRequest request);

    GetTeamByUniqNumberResponse getTeamByUniqNumber(Integer uniqNumber);

    SetTeamActivityResponse setTeamActivity(Integer uniqNumber, boolean value);

    void employeeInTeam(Integer teamUniqNumber, Integer employeeUniqNumber, boolean status);
}
