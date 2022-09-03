package ru.diasoft.spring.commonsspringbootauthoconfigure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.diasoft.spring.commonsspringbootauthoconfigure.config.AopConfig;
import ru.diasoft.spring.commonsspringbootauthoconfigure.config.RestExceptionHandlerConfig;

@Configuration
@ComponentScan("ru.diasoft.spring.commonsspringbootauthoconfigure.exception")
@Import({AopConfig.class, RestExceptionHandlerConfig.class})
public class CommonsAutoConfiguration {

}
