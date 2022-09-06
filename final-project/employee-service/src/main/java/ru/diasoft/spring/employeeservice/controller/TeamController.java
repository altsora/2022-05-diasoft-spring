package ru.diasoft.spring.employeeservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.response.BaseResponse;
import ru.diasoft.spring.employeeservice.model.request.AddTeamRequest;
import ru.diasoft.spring.employeeservice.model.response.AddTeamResponse;
import ru.diasoft.spring.employeeservice.model.response.GetTeamByUniqNumberResponse;
import ru.diasoft.spring.employeeservice.model.response.SetTeamActivityResponse;
import ru.diasoft.spring.employeeservice.service.TeamService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class TeamController {
    private final TeamService teamService;

    @PostMapping
    public AddTeamResponse addTeam(@RequestBody @Valid AddTeamRequest request) {
        return teamService.addTeam(request);
    }

    @GetMapping("/{uniq}")
    public GetTeamByUniqNumberResponse getTeamByUniqNumber(@PathVariable("uniq") Integer uniqNumber) {
        return teamService.getTeamByUniqNumber(uniqNumber);
    }

    @PutMapping("/{uniq}/activity")
    public SetTeamActivityResponse setTeamActivity(@PathVariable("uniq") Integer uniqNumber,
                                                   @RequestParam("value") boolean value) {
        return teamService.setTeamActivity(uniqNumber, value);
    }

    @PostMapping("/{teamId}/employee/{employeeId}")
    public BaseResponse employeeInTeam(@PathVariable("teamId") Integer teamId,
                                       @PathVariable("employeeId") Integer employeeId,
                                       @RequestParam("status") boolean status) {
        return teamService.employeeInTeam(teamId, employeeId, status);
    }
}
