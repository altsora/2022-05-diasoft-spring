package ru.diasoft.spring.employeeservice.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.diasoft.spring.commonsspringbootauthoconfigure.exception.DomainNotFoundException;
import ru.diasoft.spring.employeeservice.domain.Notification;
import ru.diasoft.spring.employeeservice.mapper.NotificationMapper;
import ru.diasoft.spring.employeeservice.model.response.CreateNotificationResponse;
import ru.diasoft.spring.employeeservice.repository.EmployeeRepository;
import ru.diasoft.spring.employeeservice.repository.NotificationRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Тестирование сервиса уведомлений NotificationServiceImpl")
class NotificationServiceImplTest {
    private final NotificationRepository notificationRepository = mock(NotificationRepository.class);
    private final EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
    private final NotificationMapper notificationMapper = Mappers.getMapper(NotificationMapper.class);
    private final NotificationService service = new NotificationServiceImpl(
            notificationRepository,
            employeeRepository,
            notificationMapper
    );

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(notificationRepository);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    @DisplayName("Создание уведомления: успешно")
    void createNotification_OkTest() {
        final Integer employeeId = 1;
        final String message = "message";
        final Notification saved = new Notification();

        doReturn(true).when(employeeRepository).existsById(employeeId);
        doReturn(saved).when(notificationRepository).saveAndFlush(any(Notification.class));

        final CreateNotificationResponse response = service.createNotification(employeeId, message);
        assertNotNull(response);
        assertTrue(response.isSuccess());

        verify(employeeRepository).existsById(employeeId);
        verify(notificationRepository).saveAndFlush(any(Notification.class));
    }

    @Test
    @DisplayName("Создание уведомления: сотрудник не найден")
    void createNotification_EmployeeNotFoundTest() {
        final Integer employeeId = 1;
        final String message = "message";

        assertThrows(DomainNotFoundException.class, () -> service.createNotification(employeeId, message));

        verify(employeeRepository).existsById(employeeId);
    }
}