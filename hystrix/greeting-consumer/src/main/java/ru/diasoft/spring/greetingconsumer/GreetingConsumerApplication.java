package ru.diasoft.spring.greetingconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class GreetingConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(GreetingConsumerApplication.class, args);
    }
}
