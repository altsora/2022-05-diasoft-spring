package ru.diasoft.spring.employeeservice.service;

import ru.diasoft.spring.employeeservice.model.request.AddTeamRequest;
import ru.diasoft.spring.employeeservice.model.response.AddTeamResponse;

public interface TeamService {
    AddTeamResponse addTeam(AddTeamRequest request);
}
