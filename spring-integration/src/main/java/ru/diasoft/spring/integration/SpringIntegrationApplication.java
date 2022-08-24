package ru.diasoft.spring.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.diasoft.spring.integration.service.SuperCorporation;

@SpringBootApplication
public class SpringIntegrationApplication {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(SpringIntegrationApplication.class, args);
        context.getBean(SuperCorporation.class).startMassStudy();
    }
}
