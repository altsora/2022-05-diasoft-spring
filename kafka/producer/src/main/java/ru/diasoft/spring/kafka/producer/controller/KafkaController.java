package ru.diasoft.spring.kafka.producer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@ConditionalOnProperty("spring.kafka.enabled")
public class KafkaController {
    @Value("${spring.kafka.topic}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/send")
    public String sendMessage(@RequestParam("msg") String msg) {
        log.info("Producer sent message [{}]", msg);
        kafkaTemplate.send(topic, msg);
        return "Сообщение [" + msg + "] отправлено в топик [" + topic + "]";
    }
}
