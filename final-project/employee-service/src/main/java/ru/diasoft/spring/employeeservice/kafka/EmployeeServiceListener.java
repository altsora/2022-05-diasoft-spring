package ru.diasoft.spring.employeeservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.diasoft.spring.employeeservice.service.NotificationService;
import ru.diasoft.spring.kafkaservice.data.EmployeeNotificationPayload;
import ru.diasoft.spring.kafkaservice.utils.TopicNameConstants;

@Log4j2
@Service
@RequiredArgsConstructor
public class EmployeeServiceListener {
    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;

    @SneakyThrows
    @KafkaListener(topics = TopicNameConstants.TOPIC_NAME_EMPLOYEE_NOTIFICATION)
    public void listen(@Payload String message,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Из топика {} пришло сообщение {}", topic, message);
        final EmployeeNotificationPayload payload = objectMapper.readValue(message, EmployeeNotificationPayload.class);
        notificationService.createNotification(payload.getEmployeeId(), payload.getMessage());
    }
}
