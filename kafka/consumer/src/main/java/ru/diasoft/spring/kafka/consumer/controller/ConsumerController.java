package ru.diasoft.spring.kafka.consumer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {
    @GetMapping("/")
    public String ping() {
        return "I am consumer service!";
    }
}
