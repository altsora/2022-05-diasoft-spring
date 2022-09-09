package ru.diasoft.spring.employeeservice.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.diasoft.spring.employeeservice.domain.Notification;
import ru.diasoft.spring.employeeservice.model.response.CreateNotificationResponse;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование маппера NotificationMapper")
class NotificationMapperTest {
    private final NotificationMapper mapper = Mappers.getMapper(NotificationMapper.class);

    @Test
    void fromDomainToCreateNotificationResponseTest() {
        final Notification expected = Notification.builder()
                .id(1)
                .uniqNumber(2)
                .employeeId(3)
                .dateTime(LocalDateTime.of(2022, 1, 2, 3, 4, 5))
                .message("message")
                .build();

        final CreateNotificationResponse actual = mapper.fromDomainToCreateNotificationResponse(expected);
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUniqNumber(), actual.getUniqNumber());
        assertEquals(expected.getEmployeeId(), actual.getEmployeeId());
        assertEquals(expected.getDateTime(), actual.getDateTime());
        assertEquals(expected.getMessage(), actual.getMessage());
    }
}