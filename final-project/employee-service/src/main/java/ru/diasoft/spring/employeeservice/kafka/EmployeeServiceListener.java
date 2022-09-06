package ru.diasoft.spring.employeeservice.kafka;

import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.diasoft.spring.kafkaservice.utils.TopicNameConstants;

@Log4j2
@Service
public class EmployeeServiceListener {
    @KafkaListener(topics = TopicNameConstants.TOPIC_NAME_EMPLOYEE_NOTIFICATION)
    public void listen(@Payload String message,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Consumer received message [{}] from topic [{}]", message, topic);
    }
}
