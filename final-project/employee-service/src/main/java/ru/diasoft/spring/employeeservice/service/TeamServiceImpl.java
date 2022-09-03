package ru.diasoft.spring.employeeservice.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.diasoft.spring.commonsspringbootauthoconfigure.aop.Loggable;
import ru.diasoft.spring.commonsspringbootauthoconfigure.exception.DomainNotFoundException;
import ru.diasoft.spring.commonsspringbootauthoconfigure.utils.CommonUtils;
import ru.diasoft.spring.employeeservice.domain.Team;
import ru.diasoft.spring.employeeservice.mapper.TeamMapper;
import ru.diasoft.spring.employeeservice.model.request.AddTeamRequest;
import ru.diasoft.spring.employeeservice.model.response.AddTeamResponse;
import ru.diasoft.spring.employeeservice.model.response.GetTeamByUniqNumberResponse;
import ru.diasoft.spring.employeeservice.model.response.SetTeamActivityResponse;
import ru.diasoft.spring.employeeservice.repository.TeamRepository;

import static ru.diasoft.spring.employeeservice.utils.Constants.TEAM_NAME_EXISTS;

@Log4j2
@Loggable
@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private static int uniqNumberTeamCount = 0; //TODO сделать генерацию номера

    /**
     * Добавление новой команды
     *
     * @param request входные данные
     */
    @Override
    public AddTeamResponse addTeam(@NonNull AddTeamRequest request) {
        final String name = CommonUtils.trimString(request.getName());
        if (teamRepository.existsByName(name)) {
            final AddTeamResponse response = new AddTeamResponse();
            response.setRetMessage(String.format(TEAM_NAME_EXISTS, name));
            return response;
        }

        final Team domain = teamMapper.fromAddTeamRequestToDomain(request);
        domain.setActive(true);
        domain.setUniqNumber(++uniqNumberTeamCount);
        final Team saved = teamRepository.saveAndFlush(domain);

        final AddTeamResponse response = teamMapper.fromDomainToAddTeamResponse(saved);
        response.success();
        return response;
    }

    /**
     * Получение информации о команде
     *
     * @param uniqNumber уникальный номер команды
     */
    @Override
    public GetTeamByUniqNumberResponse getTeamByUniqNumber(@NonNull Integer uniqNumber) {
        return teamRepository.findByUniqNumber(uniqNumber)
                .map(teamMapper::fromDomainToGetTeamByUniqNumberResponse)
                .map(response -> {
                    response.success();
                    return response;
                })
                .orElseThrow(() -> DomainNotFoundException.uniqNumber(Team.class, uniqNumber));
    }

    /**
     * Метод устанавливает статус активности указанной команде.
     *
     * @param uniqNumber уникальный номер команды
     * @param value      флаг активности
     */
    @Override
    public SetTeamActivityResponse setTeamActivity(@NonNull Integer uniqNumber, boolean value) {
        if (!teamRepository.existsByUniqNumber(uniqNumber)) {
            throw DomainNotFoundException.uniqNumber(Team.class, uniqNumber);
        }
        teamRepository.setActivity(uniqNumber, value);


        return null;
    }
}
