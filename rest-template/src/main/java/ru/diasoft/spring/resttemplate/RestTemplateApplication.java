package ru.diasoft.spring.resttemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.diasoft.spring.resttemplate.service.QuizService;

@SpringBootApplication
public class RestTemplateApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(RestTemplateApplication.class, args);
        ctx.getBean(QuizService.class).getClues();
        ctx.close();
    }
}
