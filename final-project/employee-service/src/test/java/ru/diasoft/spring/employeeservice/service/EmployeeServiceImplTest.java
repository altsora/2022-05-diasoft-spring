package ru.diasoft.spring.employeeservice.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.diasoft.spring.commonsspringbootauthoconfigure.exception.DomainNotFoundException;
import ru.diasoft.spring.employeeservice.domain.Employee;
import ru.diasoft.spring.employeeservice.mapper.EmployeeMapper;
import ru.diasoft.spring.employeeservice.model.request.AddEmployeeRequest;
import ru.diasoft.spring.employeeservice.model.request.LoginRequest;
import ru.diasoft.spring.employeeservice.model.response.AddEmployeeResponse;
import ru.diasoft.spring.employeeservice.model.response.GetEmployeeByIdResponse;
import ru.diasoft.spring.employeeservice.model.response.SetEmployeeActivityResponse;
import ru.diasoft.spring.employeeservice.repository.EmployeeRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Тестирование сервиса сотрудников EmployeeServiceImpl")
class EmployeeServiceImplTest {
    private final EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
    private final EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);
    private final EmployeeService employeeService = new EmployeeServiceImpl(
            employeeRepository,
            employeeMapper
    );

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    @DisplayName("Добавление сотрудника")
    void addEmployeeTest() {
        final AddEmployeeRequest request = AddEmployeeRequest.builder()
                .username("username")
                .password("password")
                .firstName("firstName")
                .lastName("lastName")
                .middleName("middleName")
                .build();
        final Employee saved = Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .middleName(request.getMiddleName())
                .build();

        doReturn(false).when(employeeRepository).existsByUsername(request.getUsername());
        doReturn(saved).when(employeeRepository).saveAndFlush(any(Employee.class));

        final AddEmployeeResponse actual = employeeService.addEmployee(request);
        assertNotNull(actual);
        assertTrue(actual.isSuccess());
        assertEquals(
                request.getLastName() + " " + request.getFirstName() + " " + request.getMiddleName(),
                actual.getFullName()
        );

        verify(employeeRepository).existsByUsername(request.getUsername());
        verify(employeeRepository).saveAndFlush(any(Employee.class));
    }

    @Test
    @DisplayName("Получить сотрудника по ID: сотрудник найден")
    void getEmployeeById_OKTest() {
        final Integer employeeId = 1;
        final Employee domain = Employee.builder()
                .firstName("firstName")
                .lastName("lastName")
                .middleName("middleName")
                .build();

        doReturn(Optional.of(domain)).when(employeeRepository).findById(employeeId);

        final GetEmployeeByIdResponse actual = employeeService.getEmployeeById(employeeId);
        assertNotNull(actual);
        assertTrue(actual.isSuccess());
        assertEquals(
                domain.getLastName() + " " + domain.getFirstName() + " " + domain.getMiddleName(),
                actual.getFullName()
        );

        verify(employeeRepository).findById(employeeId);
    }

    @Test
    @DisplayName("Получить сотрудника по ID: сотрудник не найден")
    void getEmployeeById_NotFoundTest() {
        final Integer employeeId = 1;

        doReturn(Optional.empty()).when(employeeRepository).findById(employeeId);

        assertThrows(DomainNotFoundException.class, () -> employeeService.getEmployeeById(employeeId));

        verify(employeeRepository).findById(employeeId);
    }

    @Test
    @DisplayName("Установка статуса активности сотруднику: сотрудник найден")
    void setActivity_OKTest() {
        final Integer employeeId = 1;
        final boolean value = true;

        doReturn(true).when(employeeRepository).existsById(employeeId);

        final SetEmployeeActivityResponse actual = employeeService.setActivity(employeeId, value);

        assertNotNull(actual);
        assertTrue(actual.isSuccess());
        assertEquals(value, actual.isActivity());

        verify(employeeRepository).existsById(employeeId);
        verify(employeeRepository).setActivity(employeeId, value);
    }

    @Test
    @DisplayName("Установка статуса активности сотруднику: сотрудник не найден")
    void setActivity_NotFoundTest() {
        final Integer employeeId = 1;
        final boolean value = true;

        doReturn(false).when(employeeRepository).existsById(employeeId);

        assertThrows(DomainNotFoundException.class, () -> employeeService.setActivity(employeeId, value));

        verify(employeeRepository).existsById(employeeId);
    }

    @Test
    @DisplayName("Проверка логина и пароля")
    void loginTest() {
        final Integer expected = 1;
        final LoginRequest request = LoginRequest.builder()
                .username("username")
                .password("password")
                .build();

        doReturn(Optional.of(expected)).when(employeeRepository).findIdByUsernameAndPassword(request.getUsername(), request.getPassword());

        final Integer actual = employeeService.login(request);
        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(employeeRepository).findIdByUsernameAndPassword(request.getUsername(), request.getPassword());
    }

    @Test
    @DisplayName("Проверка существования пользователя по ID")
    void employeeExistsTest() {
        final Integer employeeId = 1;

        doReturn(true).when(employeeRepository).existsById(employeeId);

        final Boolean actual = employeeService.employeeExists(employeeId);
        assertTrue(actual);

        verify(employeeRepository).existsById(employeeId);
    }
}