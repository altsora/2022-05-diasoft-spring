package ru.diasoft.spring.kafkaservice;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import javax.annotation.PostConstruct;

import static ru.diasoft.spring.kafkaservice.utils.TopicNameConstants.TOPIC_NAME_EMPLOYEE_NOTIFICATION;

@Log4j2
@Configuration
@ConditionalOnBean(KafkaConfiguration.class)
public class TopicConfig {
    @PostConstruct
    private void init() {
        log.info("Initializing TopicConfig 'topicConfig'");
    }

    @Bean
    public NewTopic employeeNotification() {
        return TopicBuilder.name(TOPIC_NAME_EMPLOYEE_NOTIFICATION)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
