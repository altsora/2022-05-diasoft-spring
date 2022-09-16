package ru.diasoft.spring.kafkaservice;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;

import javax.annotation.PostConstruct;

@Log4j2
@Configuration
@ComponentScan("ru.diasoft.spring.kafkaservice")
@EnableKafka
@Import({KafkaProducerConfig.class, KafkaConsumerConfig.class, TopicConfig.class})
public class KafkaConfiguration {
    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    @Getter
    private String bootstrapAddress;

    @Value("${spring.kafka.consumer.group-id:final-project}")
    @Getter
    private String groupId;

    @PostConstruct
    private void init() {
        log.info("Initializing KafkaConfiguration 'kafkaConfiguration'");
    }
}
