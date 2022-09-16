package ru.diasoft.spring.employeeservice.mapper;

import org.mapstruct.Mapper;
import ru.diasoft.spring.employeeservice.domain.Notification;
import ru.diasoft.spring.employeeservice.model.response.CreateNotificationResponse;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    CreateNotificationResponse fromDomainToCreateNotificationResponse(Notification domain);
}
