package ru.diasoft.spring.kafkaservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.diasoft.spring.kafkaservice.KafkaConfiguration;

import javax.annotation.PostConstruct;

@Log4j2
@Service
@RequiredArgsConstructor
@ConditionalOnBean(KafkaConfiguration.class)
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    private void init() {
        log.info("Initializing KafkaProducer 'kafkaProducer'");
    }

    public void send(String topic, Object payload) {
        try {
            final String json = mapper.writeValueAsString(payload);
            kafkaTemplate.send(topic, json);
            log.info("Сообщение {} отправлено в топик {}", payload, topic);
        } catch (JsonProcessingException ex) {
            log.error("Произошла ошибка при отправке в топик {} сообщения {}", topic, payload, ex);
        }
    }
}
