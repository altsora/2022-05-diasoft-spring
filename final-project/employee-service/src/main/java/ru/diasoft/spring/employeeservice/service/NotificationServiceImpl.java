package ru.diasoft.spring.employeeservice.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.diasoft.spring.commonsspringbootauthoconfigure.aop.Loggable;
import ru.diasoft.spring.commonsspringbootauthoconfigure.exception.DomainNotFoundException;
import ru.diasoft.spring.employeeservice.domain.Employee;
import ru.diasoft.spring.employeeservice.domain.Notification;
import ru.diasoft.spring.employeeservice.mapper.NotificationMapper;
import ru.diasoft.spring.employeeservice.model.response.CreateNotificationResponse;
import ru.diasoft.spring.employeeservice.repository.EmployeeRepository;
import ru.diasoft.spring.employeeservice.repository.NotificationRepository;

import java.time.LocalDateTime;

@Log4j2
@Loggable
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final EmployeeRepository employeeRepository;
    private final NotificationMapper notificationMapper;
    private static int uniqNumberNotificationCount = 0; //TODO сделать генерацию номера

    /**
     * Метод создаёт новое уведомление в базе.
     *
     * @param employeeId ID сотрудника
     * @param message    текст уведомления
     */
    @Override
    public CreateNotificationResponse createNotification(@NonNull Integer employeeId, @NonNull String message) {
        if (!employeeRepository.existsById(employeeId)) {
            throw DomainNotFoundException.id(Employee.class, employeeId);
        }
        final Notification domain = Notification.builder()
                .uniqNumber(++uniqNumberNotificationCount)
                .employeeId(employeeId)
                .dateTime(LocalDateTime.now())
                .message(message)
                .build();
        final Notification saved = notificationRepository.saveAndFlush(domain);
        final CreateNotificationResponse response = notificationMapper.fromDomainToCreateNotificationResponse(saved);
        response.success();
        return response;
    }
}
