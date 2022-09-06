package ru.diasoft.spring.employeeservice.service;

import ru.diasoft.spring.employeeservice.model.response.CreateNotificationResponse;

public interface NotificationService {
    CreateNotificationResponse createNotification(Integer employeeId, String message);
}
