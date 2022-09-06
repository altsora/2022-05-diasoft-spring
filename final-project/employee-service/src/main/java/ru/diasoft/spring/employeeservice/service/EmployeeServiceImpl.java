package ru.diasoft.spring.employeeservice.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.spring.commonsspringbootauthoconfigure.aop.Loggable;
import ru.diasoft.spring.commonsspringbootauthoconfigure.exception.DomainNotFoundException;
import ru.diasoft.spring.commonsspringbootauthoconfigure.utils.CommonUtils;
import ru.diasoft.spring.employeeservice.domain.Employee;
import ru.diasoft.spring.employeeservice.mapper.EmployeeMapper;
import ru.diasoft.spring.employeeservice.mapper.TeamMapper;
import ru.diasoft.spring.employeeservice.model.request.AddEmployeeRequest;
import ru.diasoft.spring.employeeservice.model.request.LoginRequest;
import ru.diasoft.spring.employeeservice.model.request.UpdateEmployeeRequest;
import ru.diasoft.spring.employeeservice.model.response.AddEmployeeResponse;
import ru.diasoft.spring.employeeservice.model.response.GetEmployeeByIdResponse;
import ru.diasoft.spring.employeeservice.model.response.SetEmployeeActivityResponse;
import ru.diasoft.spring.employeeservice.model.response.UpdateEmployeeResponse;
import ru.diasoft.spring.employeeservice.repository.EmployeeRepository;
import ru.diasoft.spring.employeeservice.utils.Functions;

import static ru.diasoft.spring.employeeservice.utils.Constants.EMPLOYEE_USERNAME_EXISTS;

@Log4j2
@Loggable
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final TeamMapper teamMapper;
    private static int uniqNumberEmployeeCount = 0; //TODO сделать генерацию номера

    /**
     * Добавление сотрудника.
     * <p>
     * Если сотрудник с указанным логином существует, то новый не будет добавлен.
     *
     * @param request входные данные
     */
    @Override
    @Transactional
    public AddEmployeeResponse addEmployee(@NonNull AddEmployeeRequest request) {
        final String username = CommonUtils.trimString(request.getUsername());
        if (employeeRepository.existsByUsername(username)) {
            final AddEmployeeResponse response = new AddEmployeeResponse();
            response.setRetMessage(String.format(EMPLOYEE_USERNAME_EXISTS, username));
            return response;
        }

        final Employee domain = employeeMapper.fromAddEmployeeRequestToDomain(request);
        domain.setActive(true);
        domain.setUniqNumber(++uniqNumberEmployeeCount);
        final Employee saved = employeeRepository.saveAndFlush(domain);

        final AddEmployeeResponse response = employeeMapper.fromDomainToAddEmployeeResponse(saved);
        response.success();
        response.setFullName(CommonUtils.trimString(Functions.FULL_NAME.apply(saved)));
        return response;
    }

    /**
     * Поиск сотрудника по ID и возвращение информации по нему.
     *
     * @param employeeId ID сотрудника
     * @throws DomainNotFoundException если сотрудник не найден
     */
    @Override
    public GetEmployeeByIdResponse getEmployeeById(@NonNull Integer employeeId) {
        final Employee domain = employeeRepository.findById(employeeId)
                .orElseThrow(() -> DomainNotFoundException.id(Employee.class, employeeId));
        final GetEmployeeByIdResponse response = employeeMapper.fromDomainToGetEmployeeByIdResponse(domain);
        response.success();
        response.setFullName(CommonUtils.trimString(Functions.FULL_NAME.apply(domain)));
        return response;
    }

    /**
     * Обновление данных сотрудника.
     *
     * @param employeeId ID сотрудника
     * @param request    входные данные
     * @throws DomainNotFoundException если сотрудник не найден
     */
    @Override
    @Transactional
    public UpdateEmployeeResponse updateEmployee(@NonNull Integer employeeId, @NonNull UpdateEmployeeRequest request) {
        final Employee domain = employeeRepository.findById(employeeId)
                .orElseThrow(() -> DomainNotFoundException.id(Employee.class, employeeId));
        domain.setFirstName(CommonUtils.trimString(request.getFirstName()));
        domain.setLastName(CommonUtils.trimString(request.getLastName()));
        domain.setMiddleName(CommonUtils.trimString(request.getMiddleName()));
        final Employee saved = employeeRepository.saveAndFlush(domain);

        final UpdateEmployeeResponse response = employeeMapper.fromDomainToUpdateEmployeeResponse(saved);
        response.success();
        response.setFullName(CommonUtils.trimString(Functions.FULL_NAME.apply(domain)));
        return response;
    }

    /**
     * Метод устанавливает статус активности указанному сотруднику.
     *
     * @param employeeId ID сотрудника
     * @param value      флаг активности
     * @throws DomainNotFoundException если сотрудник не найден
     */
    @Override
    @Transactional
    public SetEmployeeActivityResponse setActivity(@NonNull Integer employeeId, boolean value) {
        if (!employeeRepository.existsById(employeeId)) {
            throw DomainNotFoundException.id(Employee.class, employeeId);
        }
        employeeRepository.setActivity(employeeId, value);

        final SetEmployeeActivityResponse response = new SetEmployeeActivityResponse();
        response.success();
        response.setActivity(value);
        return response;
    }

    /**
     * Возвращает ID пользователя по логину и паролю, иначе null.
     *
     * @param request входные данные
     */
    @Override
    public Integer login(@NonNull LoginRequest request) {
        return employeeRepository.findIdByUsernameAndPassword(request.getUsername(), request.getPassword())
                .orElse(null);
    }

    @Override
    public Boolean employeeExists(@NonNull Integer employeeId) {
        return employeeRepository.existsById(employeeId);
    }
}
