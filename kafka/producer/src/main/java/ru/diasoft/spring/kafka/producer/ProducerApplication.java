package ru.diasoft.spring.kafka.producer;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@Log4j2
@SpringBootApplication
public class ProducerApplication {
    @Value("${spring.kafka.enabled}")
    private boolean kafkaEnabled;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @PostConstruct
    private void init() {
        log.info("kafka enabled: {}", kafkaEnabled);
        log.info("bootstrapAddress = [{}]", bootstrapAddress);
    }

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }
}
