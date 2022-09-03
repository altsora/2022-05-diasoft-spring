package ru.diasoft.spring.integration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;
import ru.diasoft.spring.integration.domain.Programmer;
import ru.diasoft.spring.integration.service.MonitoringService;

import java.util.concurrent.Executors;

import static ru.diasoft.spring.integration.utils.Constants.*;

@Configuration
public class IntegrationConfig {
    private static final int QUEUE_CAPACITY = 10;

    @Bean(PROGRAMMERS_CHANNEL)
    public MessageChannel programmersChanel() {
        return MessageChannels.queue(QUEUE_CAPACITY).get();
    }

    @Bean(FINISH_STUDY_CHANNEL)
    public PublishSubscribeChannel finishStudyChanel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public IntegrationFlow longWayFlow() {
        return IntegrationFlows.from(PROGRAMMERS_CHANNEL)
                .log()
                .split()
                .channel(MessageChannels.executor(Executors.newCachedThreadPool()))
                .log()
                .handle(BEAN_NAME_JUNIOR_STUDY_SERVICE, METHOD_NAME_STUDY)
                .log()
                .handle(BEAN_NAME_MIDDLE_STUDY_SERVICE, METHOD_NAME_STUDY)
                .log()
                .handle(BEAN_NAME_SENIOR_STUDY_SERVICE, METHOD_NAME_STUDY)
                .log()
                .<Programmer, Boolean>route(Programmer::hasLastGrade, msg -> msg
                        .subFlowMapping(true, successProgrammerFlow())
                        .subFlowMapping(false, loserProgrammerFlow()))
                .get();
    }

    @Bean
    public IntegrationFlow successProgrammerFlow() {
        return f -> f.<Programmer>handle((payload, headers) -> payload.reward())
                .channel(AGGREGATE_FLOW_INPUT);
    }

    public IntegrationFlow loserProgrammerFlow() {
        return f -> f.<Programmer>handle((payload, headers) -> payload.noReward())
                .channel(AGGREGATE_FLOW_INPUT);
    }

    @Bean
    public IntegrationFlow aggregateFlow(MonitoringService monitoringService) {
        return f -> f.aggregate()
                .channel(FINISH_STUDY_CHANNEL)
                .handle(monitoringService);
    }
}
