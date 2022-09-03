package ru.diasoft.spring.commonsspringbootauthoconfigure.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("ru.diasoft.spring.commonsspringbootauthoconfigure.exception")
@ConditionalOnProperty("commons.rest-exception-handler.enabled")
public class RestExceptionHandlerConfig {
}
