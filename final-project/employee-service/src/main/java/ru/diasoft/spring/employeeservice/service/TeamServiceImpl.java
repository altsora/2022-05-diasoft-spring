package ru.diasoft.spring.employeeservice.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.spring.commonsspringbootauthoconfigure.aop.Loggable;
import ru.diasoft.spring.commonsspringbootauthoconfigure.utils.CommonUtils;
import ru.diasoft.spring.employeeservice.domain.Team;
import ru.diasoft.spring.employeeservice.mapper.TeamMapper;
import ru.diasoft.spring.employeeservice.model.request.AddTeamRequest;
import ru.diasoft.spring.employeeservice.model.response.AddTeamResponse;
import ru.diasoft.spring.employeeservice.repository.TeamRepository;

import java.util.concurrent.atomic.AtomicInteger;

import static ru.diasoft.spring.employeeservice.utils.Constants.TEAM_NAME_EXISTS;

@Log4j2
@Loggable
@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final AtomicInteger uniqNumberTeamCount = new AtomicInteger(0);
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    /**
     * Добавление новой команды
     *
     * @param request входные данные
     */
    @Override
    @Transactional
    public AddTeamResponse addTeam(@NonNull AddTeamRequest request) {
        final String name = CommonUtils.trimString(request.getName());
        if (teamRepository.existsByName(name)) {
            final AddTeamResponse response = new AddTeamResponse();
            response.setRetMessage(String.format(TEAM_NAME_EXISTS, name));
            return response;
        }

        final Team domain = teamMapper.fromAddTeamRequestToDomain(request);
        domain.setActive(true);
        domain.setUniqNumber(uniqNumberTeamCount.incrementAndGet());
        final Team saved = teamRepository.saveAndFlush(domain);

        final AddTeamResponse response = teamMapper.fromDomainToAddTeamResponse(saved);
        response.success();
        return response;
    }
}
